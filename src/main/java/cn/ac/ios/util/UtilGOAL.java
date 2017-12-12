package cn.ac.ios.util;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import dk.brics.automaton.Automaton;
import dk.brics.automaton.State;
import dk.brics.automaton.Transition;
import gnu.trove.map.hash.TIntObjectHashMap;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class UtilGOAL {
    static public String gfffromDK(Automaton A){

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element
            Element root = doc.createElement("Structure");
            root.setAttribute("label-on","Transition");
            root.setAttribute("type","FiniteStateAutomaton");
            doc.appendChild(root);

            Element e = doc.createElement("Name");
            root.appendChild(e);

            e = doc.createElement("Description");
            root.appendChild(e);

            e = doc.createElement("Formula");
            root.appendChild(e);

            Element Alphabet = doc.createElement("Alphabet");
            Alphabet.setAttribute("type","Classical");

            root.appendChild(Alphabet);

            Element StateSet = doc.createElement("StateSet");
            root.appendChild(StateSet);
            ArrayList<State> states = new ArrayList<>();
            HashMap<State,Integer> stateIndex = new HashMap<>();

            int n = 0, initalID = -1;
            for (State s : A.getStates()){
                Element stateNode = doc.createElement("State");
                stateNode.setAttribute("sid",String.valueOf(n));
                StateSet.appendChild(stateNode);

                if (A.getInitialState().equals(s)){
                    initalID = n;
                }

                states.add(s);
                stateIndex.put(s,n++);
            }

            Element InitialStateSet = doc.createElement("InitialStateSet");
            Element StateID = doc.createElement("StateID");
            StateID.setTextContent(String.valueOf(initalID));
            root.appendChild(InitialStateSet);

            Element TransitionSet = doc.createElement("TransitionSet");
            root.appendChild(TransitionSet);

            HashSet<Integer> alphabetSet = new HashSet<>();

            Element Acc = doc.createElement("Acc");
            Acc.setAttribute("type","Buchi");
            root.appendChild(Acc);

            int m = 1;
            for (int i = 0; i < n; i++) {
                State state = states.get(i);
                if (state.isAccept()){
                    Element ID = doc.createElement("StateID");
                    ID.setTextContent(String.valueOf(i));
                    Acc.appendChild(ID);
                }
                for (Transition t : state.getTransitions()){
                    int a = t.getMin();
                    int b = t.getMax();
                    int j = stateIndex.get(t.getDest());
                    for (int letter = a; letter <=b ; letter++) {
                        alphabetSet.add(letter);
                        Element transitionNode = doc.createElement("Transition");
                        transitionNode.setAttribute("tid",String.valueOf(m++));
                        Element temp = doc.createElement("From");
                        temp.setTextContent(String.valueOf(i));
                        transitionNode.appendChild(temp);

                        temp = doc.createElement("To");
                        temp.setTextContent(String.valueOf(j));
                        transitionNode.appendChild(temp);

                        temp = doc.createElement("Label");
                        temp.setTextContent(String.valueOf(letter));
                        transitionNode.appendChild(temp);

                        TransitionSet.appendChild(transitionNode);
                    }
                }
            }

            for (Integer i : alphabetSet){
                Element s = doc.createElement("Symbol");
                s.setTextContent(String.valueOf(i));
                Alphabet.appendChild(s);
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("/Users/lijianlin/GOAL/test.gff"));
            transformer.transform(source, result);

            // Output to console for testing
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
