package converter;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.LinkSource;
import guru.nidi.graphviz.model.Node;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;
import static guru.nidi.graphviz.model.Link.to;

public class Converter {
    private static final String PATH_TO_OUTPUT = "output/converter";

    public static void main(String[] args) {
        File input = new File("input.txt");
        try (FileInputStream inputStream = new FileInputStream(input)) {
            Scanner scanner = new Scanner(inputStream);

            int inputsCount = scanner.nextInt();
            scanner.nextInt();
            int nodesCount = scanner.nextInt();

            String machineType = scanner.next().trim();

            if (machineType.equals("moore")) {
                List<MooreEdge> mooreEdges = parseMoore(scanner, inputsCount, nodesCount);
                printMooreToMealyGraph(mooreEdges);
                printMooreToMealyTable(inputsCount, mooreEdges);
            } else if (machineType.equals("mealy")) {
                List<MealyEdge> mealyEdges = parseMealy(scanner, inputsCount, nodesCount);
                List<MooreEdge> mooreEdges = printMealyToMooreGraph(mealyEdges);
                printMealyToMooreTable(inputsCount, mooreEdges);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void printMealyToMooreTable(int inputsCount, List<MooreEdge> mooreEdges) throws IOException {
        File output = new File(PATH_TO_OUTPUT + "/output.txt");

        var sortedMooreEdges = mooreEdges.stream().sorted((left, right) -> {
            int a = Integer.parseInt(left.x.substring(1));
            int b = Integer.parseInt(right.x.substring(1));
            return Integer.compare(a, b);
        }).collect(Collectors.toList());

        try (FileWriter writer = new FileWriter(output)) {
            int index = 0;
            for (MooreEdge mooreEdge : sortedMooreEdges) {
                writer.append(mooreEdge.to.q);
                if ((index + 1) % (mooreEdges.size() / inputsCount) == 0) {
                    writer.append("\n");
                } else {
                    writer.append(" ");
                }
                ++index;
            }
        }
    }

    private static void printMooreToMealyTable(int inputsCount, List<MooreEdge> mooreEdges) throws IOException {
        File output = new File(PATH_TO_OUTPUT + "/output.txt");

        try (FileWriter writer = new FileWriter(output)) {
            int index = 0;
            for (MooreEdge mooreEdge : mooreEdges) {
                writer.append(mooreEdge.to.q).append(mooreEdge.to.y);
                if ((index + 1) % (mooreEdges.size() / inputsCount) == 0) {
                    writer.append("\n");
                } else {
                    writer.append(" ");
                }
                ++index;
            }
        }
    }

    private static List<MealyEdge> parseMealy(Scanner scanner, Integer inputsCount, Integer nodesCount) throws IOException {
        List<MealyNode> mealyNodes = new ArrayList<>();
        List<MealyEdge> mealyEdges = new ArrayList<>();

        fillMealyNodes(nodesCount, mealyNodes);
        fillMealyEdges(scanner, inputsCount, nodesCount, mealyNodes, mealyEdges);

        return mealyEdges;
    }

    private static void fillMealyEdges(Scanner scanner,
                                       int inputsCount,
                                       int nodesCount,
                                       List<MealyNode> mealyNodes,
                                       List<MealyEdge> mealyEdges) throws IOException {
        var delimiter = scanner.delimiter();
        for (int i = 0; i < inputsCount; i++) {
            for (int j = 0; j < nodesCount; j++) {
                String q = scanner.useDelimiter("y").next().trim();
                String y = scanner.useDelimiter(delimiter).next().trim();

                MealyEdge mealyEdge = new MealyEdge();

                mealyEdge.x = "x" + (i + 1);
                mealyEdge.y = y;

                mealyEdge.to = findMealyNode(mealyNodes, q);
                mealyEdge.from = mealyNodes.get(j);

                mealyEdges.add(mealyEdge);
            }
        }
    }

    private static MealyNode findMealyNode(List<MealyNode> mealyNodes, String q) throws IOException {
        Optional<MealyNode> to = mealyNodes
            .stream()
            .filter(mealyNode -> mealyNode.q.equals(q))
            .findFirst();
        if (to.isEmpty()) {
            throw new IOException("Node " + q + " not found");
        }
        return to.get();
    }

    private static void fillMealyNodes(int nodesCount, List<MealyNode> mealyNodes) {
        for (int i = 0; i < nodesCount; i++) {
            MealyNode mealyNode = new MealyNode();

            mealyNode.q = "s" + i;

            mealyNodes.add(mealyNode);
        }
    }

    private static List<MooreEdge> parseMoore(Scanner scanner, int inputsCount, int nodesCount) throws IOException {
        ArrayList<MooreNode> mooreNodes = new ArrayList<>();
        ArrayList<MooreEdge> mooreEdges = new ArrayList<>();

        fillMooreNodes(scanner, nodesCount, mooreNodes);
        fillMooreEdges(scanner, inputsCount, nodesCount, mooreNodes, mooreEdges);

        return mooreEdges;
    }

    private static void fillMooreEdges(Scanner scanner,
                                       int inputsCount,
                                       int nodesCount,
                                       List<MooreNode> mooreNodes,
                                       List<MooreEdge> mooreEdges) throws IOException {
        for (int i = 0; i < inputsCount; i++) {
            for (int j = 0; j < nodesCount; j++) {
                MooreEdge mooreEdge = new MooreEdge();

                mooreEdge.x = "x" + (i + 1);

                String q = scanner.next().trim();

                mooreEdge.to = findMooreNode(mooreNodes, q);
                mooreEdge.from = mooreNodes.get(j);

                mooreEdges.add(mooreEdge);
            }
        }
    }

    private static MooreNode findMooreNode(List<MooreNode> mooreNodes, String q) throws IOException {
        Optional<MooreNode> to = mooreNodes
            .stream()
            .filter(mooreNode -> mooreNode.q.contains(q))
            .findFirst();

        if (to.isEmpty()) {
            throw new IOException("Node " + " to not found");
        }
        return to.get();
    }

    private static void fillMooreNodes(Scanner scanner, int nodesCount, List<MooreNode> mooreNodes) {
        for (int i = 0; i < nodesCount; i++) {
            MooreNode mooreNode = new MooreNode();

            String y = scanner.next();

            mooreNode.q = "q" + i;
            mooreNode.y = y;

            mooreNodes.add(mooreNode);
        }
    }

    private static List<MooreEdge> printMealyToMooreGraph(List<MealyEdge> mealyEdges) {
        List<LinkSource> mealySources = createMealyLinkSources(mealyEdges);

        Graph mealyGraph = graph("Mealy Graph")
            .directed()
            .with(mealySources);

        try {
            Graphviz
                .fromGraph(mealyGraph)
                .width(1024)
                .render(Format.PNG)
                .toFile(new File(PATH_TO_OUTPUT + "/mealy-to-more/mealy.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<MooreEdge> mooreEdges = mealyToMoore(mealyEdges);
        List<LinkSource> mooreSources = createMooreLinkSources(mooreEdges);


        Graph mooreGraph = graph("Moore Graph")
            .directed()
            .with(mooreSources);

        try {
            Graphviz
                .fromGraph(mooreGraph)
                .width(1024)
                .render(Format.PNG)
                .toFile(new File(PATH_TO_OUTPUT + "/mealy-to-more/moore.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mooreEdges;
    }

    private static void printMooreToMealyGraph(List<MooreEdge> mooreEdges) {
        List<LinkSource> mooreSources = createMooreLinkSources(mooreEdges);

        Graph mooreGraph = graph("Moore Graph")
            .directed()
            .with(mooreSources);

        try {
            Graphviz
                .fromGraph(mooreGraph)
                .width(1024)
                .render(Format.PNG)
                .toFile(new File(PATH_TO_OUTPUT + "/moore-to-mealy/moore.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<LinkSource> mealySources = mooreToMealy(mooreEdges);

        Graph mealyGraph = graph("Mealy Graph")
            .directed()
            .with(mealySources);

        try {
            Graphviz
                .fromGraph(mealyGraph)
                .width(1024)
                .render(Format.PNG)
                .toFile(new File(PATH_TO_OUTPUT + "/moore-to-mealy/mealy.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<LinkSource> mooreToMealy(List<MooreEdge> mooreEdges) {
        List<MealyEdge> mealyEdges = new ArrayList<>();

        for (MooreEdge mooreEdge : mooreEdges) {
            MealyNode mealyFrom = new MealyNode();
            mealyFrom.q = mooreEdge.from.q;

            MealyNode mealyTo = new MealyNode();
            mealyTo.q = mooreEdge.to.q;

            MealyEdge mealyEdge = new MealyEdge();
            mealyEdge.from = mealyFrom;
            mealyEdge.to = mealyTo;
            mealyEdge.x = mooreEdge.x;
            mealyEdge.y = mooreEdge.to.y;

            mealyEdges.add(mealyEdge);
        }

        return createMealyLinkSources(mealyEdges);
    }

    private static List<MooreEdge> mealyToMoore(List<MealyEdge> mealyEdges) {
        class MooreState {
            private String y;
            private String q;

            private MooreState(String y, String q) {
                this.y = y;
                this.q = q;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof MooreState)) return false;
                MooreState that = (MooreState) o;
                return Objects.equals(y, that.y) &&
                    Objects.equals(q, that.q);
            }

            @Override
            public int hashCode() {
                return Objects.hash(y, q);
            }
        }

        List<MooreEdge> mooreEdges = new ArrayList<>();

        Set<MealyEdge> uniqueMealyEdges = new HashSet<>(mealyEdges);

        Map<MooreState, String> stateToZ = new HashMap<>();
        Map<String, MooreState> zToState = new HashMap<>();

        List<MooreNode> mooreNodes = new ArrayList<>();

        var sortedUniqueMealyEdges = uniqueMealyEdges.stream().sorted((left, right) -> {
            int a = Integer.parseInt(left.to.q.substring(1));
            int b = Integer.parseInt(right.to.q.substring(1));
            if (a > b) {
                return 1;
            } else if (a < b) {
                return -1;
            } else {
                int c = Integer.parseInt(left.y.substring(1));
                int d = Integer.parseInt(right.y.substring(1));
                return Integer.compare(c, d);
            }
        }).collect(Collectors.toList());

        int index = 0;
        for (MealyEdge uniqueMealyEdge : sortedUniqueMealyEdges) {
            MealyNode mealyFrom = uniqueMealyEdge.to;
            Optional<MealyEdge> mealyEdgeFrom = sortedUniqueMealyEdges
                .stream()
                .filter(mealyEdge -> mealyEdge.to.equals(mealyFrom))
                .findFirst();
            if (mealyEdgeFrom.isPresent()) {
                MooreState state = new MooreState(uniqueMealyEdge.y, mealyFrom.q);
                if (!stateToZ.containsKey(state)) {
                    stateToZ.put(state, "z" + index);
                    zToState.put("z" + index, state);
                    index++;
                }

                MooreNode mooreNode = new MooreNode();
                mooreNode.q = stateToZ.get(state);
                mooreNode.y = uniqueMealyEdge.y;

                mooreNodes.add(mooreNode);
            }
        }

        index = 0;
        for (MooreNode mooreFrom : mooreNodes) {
            MooreState state = zToState.get(mooreFrom.q);

            String qFrom = state.q;
            List<MealyEdge> mealyEdgeTo = mealyEdges
                .stream()
                .filter(mealyEdge -> mealyEdge.from.q.equals(qFrom))
                .collect(Collectors.toList());

            for (MealyEdge mealyEdge : mealyEdgeTo) {
                MooreEdge mooreEdge = new MooreEdge();

                MooreState mooreState = new MooreState(mealyEdge.y, mealyEdge.to.q);

                String mooreToZ = stateToZ.get(mooreState);

                Optional<MooreNode> mooreTo = mooreNodes.stream()
                    .filter(mooreNode -> mooreNode.q.equals(mooreToZ))
                    .findFirst();

                if (mooreTo.isPresent()) {
                    mooreEdge.from = mooreFrom;
                    mooreEdge.to = mooreTo.get();
                    mooreEdge.x = "x" + index++;

                    mooreEdges.add(mooreEdge);
                }
            }
            index = 0;
        }

        return mooreEdges;
    }

    private static List<LinkSource> createMooreLinkSources(List<MooreEdge> mooreEdges) {
        List<LinkSource> sources = new ArrayList<>();
        for (MooreEdge mooreEdge : mooreEdges) {
            Label label = Label.of(mooreEdge.x);
            Node from = node(mooreEdge.from.q).with("xlabel", mooreEdge.from.y);
            Node to = node(mooreEdge.to.q).with("xlabel", mooreEdge.to.y);
            sources.add(from.link(to(to).with(label)));
        }
        return sources;
    }

    private static List<LinkSource> createMealyLinkSources(List<MealyEdge> mealyEdges) {
        List<LinkSource> sources = new ArrayList<>();
        for (MealyEdge mealyEdge : mealyEdges) {
            Label label = Label.of(mealyEdge.x + "/" + mealyEdge.y);
            sources.add(node(mealyEdge.from.q).link(to(node(mealyEdge.to.q)).with(label)));
        }
        return sources;
    }

    private static class MealyNode {
        String q;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MealyNode)) return false;
            MealyNode mealyNode = (MealyNode) o;
            return Objects.equals(q, mealyNode.q);
        }

        @Override
        public int hashCode() {
            return Objects.hash(q);
        }
    }

    private static class MealyEdge {
        MealyNode from;
        MealyNode to;
        String x;
        String y;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MealyEdge)) return false;
            MealyEdge mealyEdge = (MealyEdge) o;
            return Objects.equals(to, mealyEdge.to) &&
                Objects.equals(y, mealyEdge.y);
        }

        @Override
        public int hashCode() {
            return Objects.hash(to, y);
        }
    }

    private static class MooreNode {
        String q;
        String y;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MooreNode)) return false;
            MooreNode mooreNode = (MooreNode) o;
            return Objects.equals(q, mooreNode.q) &&
                Objects.equals(y, mooreNode.y);
        }

        @Override
        public int hashCode() {
            return Objects.hash(q, y);
        }
    }

    private static class MooreEdge {
        MooreNode from;
        MooreNode to;
        String x;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MooreEdge)) return false;
            MooreEdge mooreEdge = (MooreEdge) o;
            return Objects.equals(from, mooreEdge.from) &&
                Objects.equals(to, mooreEdge.to) &&
                Objects.equals(x, mooreEdge.x);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to, x);
        }
    }
}
