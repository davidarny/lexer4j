package mealy_moore_converter;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;
import static guru.nidi.graphviz.model.Link.to;

public class Main {
    private static String PATH_TO_OUTPUT = "output";

    public static void main(String[] args) {
        File input = new File("input.txt");
        try (FileInputStream inputStream = new FileInputStream(input)) {
            Scanner scanner = new Scanner(inputStream);

            Integer inputsCount = scanner.nextInt();
            scanner.nextInt();
            Integer nodesCount = scanner.nextInt();

            String machineType = scanner.next().trim();

            if (machineType.equals("moore")) {
                List<MooreEdge> mooreEdges = parseMoore(scanner, inputsCount, nodesCount);
                printMooreToMealyGraph(mooreEdges);
                printMooreToMealyTable(mooreEdges);
            } else if (machineType.equals("mealy")) {
                List<MealyEdge> mealyEdges = parseMealy(scanner, inputsCount, nodesCount);
                printMealyToMooreGraph(mealyEdges);
                printMealyToMooreTable(mealyEdges);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void printMealyToMooreTable(List<MealyEdge> mealyEdges) throws IOException {
        File output = new File(PATH_TO_OUTPUT + "/output.txt");
        try (FileWriter writer = new FileWriter(output)) {
            Integer index = 0;
            for (MealyEdge mealyEdge : mealyEdges) {
                writer.append(mealyEdge.to.q).append(" ");
                if (index.equals(mealyEdges.size() / 2 - 1)) {
                    writer.append("\n");
                }
                ++index;
            }
        }
    }

    private static void printMooreToMealyTable(List<MooreEdge> mooreEdges) throws IOException {
        File output = new File(PATH_TO_OUTPUT + "/output.txt");
        try (FileWriter writer = new FileWriter(output)) {
            Integer index = 0;
            for (MooreEdge mooreEdge : mooreEdges) {
                writer.append(mooreEdge.to.q).append(mooreEdge.to.y).append(" ");
                if (index.equals(mooreEdges.size() / 2 - 1)) {
                    writer.append("\n");
                }
                ++index;
            }
        }
    }

    private static ArrayList<MealyEdge> parseMealy(Scanner scanner, Integer inputsCount, Integer nodesCount) {
        ArrayList<MealyNode> mealyNodes = new ArrayList<>();
        ArrayList<MealyEdge> mealyEdges = new ArrayList<>();

        fillMealyNodes(nodesCount, mealyNodes);
        fillMealyEdges(scanner, inputsCount, nodesCount, mealyNodes, mealyEdges);

        return mealyEdges;
    }

    private static void fillMealyEdges(Scanner scanner,
                                       Integer inputsCount,
                                       Integer nodesCount,
                                       List<MealyNode> mealyNodes,
                                       List<MealyEdge> mealyEdges) {
        var delimiter = scanner.delimiter();
        for (Integer i = 0; i < inputsCount; i++) {
            for (Integer j = 0; j < nodesCount; j++) {
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

    private static MealyNode findMealyNode(List<MealyNode> mealyNodes, String q) {
        Optional<MealyNode> to = mealyNodes
            .stream()
            .filter(mealyNode -> mealyNode.q.equals(q))
            .findFirst();
        if (to.isEmpty()) {
            throw new RuntimeException("Node " + q + " not found");
        }
        return to.get();
    }

    private static void fillMealyNodes(Integer nodesCount, List<MealyNode> mealyNodes) {
        for (Integer i = 0; i < nodesCount; i++) {
            MealyNode mealyNode = new MealyNode();

            mealyNode.q = "s" + i;

            mealyNodes.add(mealyNode);
        }
    }

    private static List<MooreEdge> parseMoore(Scanner scanner, Integer inputsCount, Integer nodesCount) {
        ArrayList<MooreNode> mooreNodes = new ArrayList<>();
        ArrayList<MooreEdge> mooreEdges = new ArrayList<>();

        fillMooreNodes(scanner, nodesCount, mooreNodes);
        fillMooreEdges(scanner, inputsCount, nodesCount, mooreNodes, mooreEdges);

        return mooreEdges;
    }

    private static void fillMooreEdges(Scanner scanner,
                                       Integer inputsCount,
                                       Integer nodesCount,
                                       List<MooreNode> mooreNodes,
                                       List<MooreEdge> mooreEdges) {
        for (Integer i = 0; i < inputsCount; i++) {
            for (Integer j = 0; j < nodesCount; j++) {
                MooreEdge mooreEdge = new MooreEdge();

                mooreEdge.x = "x" + (i + 1);

                String q = scanner.next().trim();

                mooreEdge.to = findMooreNode(mooreNodes, q);
                mooreEdge.from = mooreNodes.get(j);

                mooreEdges.add(mooreEdge);
            }
        }
    }

    private static MooreNode findMooreNode(List<MooreNode> mooreNodes, String q) {
        Optional<MooreNode> to = mooreNodes
            .stream()
            .filter(mooreNode -> mooreNode.q.contains(q))
            .findFirst();

        if (to.isEmpty()) {
            throw new RuntimeException("Node " + " to not found");
        }
        return to.get();
    }

    private static void fillMooreNodes(Scanner scanner, Integer nodesCount, List<MooreNode> mooreNodes) {
        for (Integer i = 0; i < nodesCount; i++) {
            MooreNode mooreNode = new MooreNode();

            String y = scanner.next();

            mooreNode.q = "q" + i;
            mooreNode.y = y;

            mooreNodes.add(mooreNode);
        }
    }

    private static void printMealyToMooreGraph(List<MealyEdge> mealyEdges) {
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

        List<LinkSource> mooreSources = mealyToMoore(mealyEdges);

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

    private static List<LinkSource> mealyToMoore(List<MealyEdge> mealyEdges) {
        List<MooreEdge> mooreEdges = new ArrayList<>();

        int index = 0;
        for (MealyEdge mealyEdge : mealyEdges) {
            MooreNode mooreFrom = new MooreNode();

            mooreFrom.q = mealyEdge.from.q;

            String y = mealyEdges.get(index).y;
            if (y == null || y.isEmpty()) {
                mooreFrom.y = "-";
            } else {
                mooreFrom.y = y;
            }

            MooreNode mooreTo = new MooreNode();
            mooreTo.q = mealyEdge.to.q;
            mooreTo.y = mealyEdge.y;

            MooreEdge mooreEdge = new MooreEdge();
            mooreEdge.from = mooreFrom;
            mooreEdge.to = mooreTo;
            mooreEdge.x = mealyEdge.x;

            mooreEdges.add(mooreEdge);

            ++index;
        }

        return createMooreLinkSources(mooreEdges);
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
    }

    private static class MealyEdge {
        MealyNode from;
        MealyNode to;
        String x;
        String y;
    }

    private static class MooreNode {
        String q;
        String y;
    }

    private static class MooreEdge {
        MooreNode from;
        MooreNode to;
        String x;
    }
}
