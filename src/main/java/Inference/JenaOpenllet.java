
/*
 * Software Name : Dependencies inference
 * Version: 0.0
 * SPDX-FileCopyrightText: Copyright (c) 2022[-2022] Orange
 * SPDX-License-Identifier: AGPL-3.0-only
 *
 * This software is distributed under the GNU Affero General Public License v3.0 only,
 * the text of which is available at https://spdx.org/licenses/AGPL-3.0-only.html
 * or see the "license.txt" file for more details.
 *
 * Author: Amal GUITTOUM
 * Software description: Dependencies inference in smart home scenarios
 */
package Inference;
import openllet.jena.PelletReasonerFactory;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.iterator.ExtendedIterator;

public class JenaOpenllet extends Swrl {
    private String data;
    private OntModel model;
    private int cpt=0;
     @Override
    public void run() {
        data = "file:src/main/resources/SWRLRule/200.owl";

        long startTime=System.nanoTime();
        model = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);
        model.read(data);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Swrl Jena Openllet load data execution Time: "+duration/1000000+ "  ms");
        System.out.println("--------------------------------------------------");
        System.out.println("Dependencies Inference results : ");
        startTime=System.nanoTime();
        infer();
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("--------------------------------------------------");
        System.out.println("Swrl Jena openllet inference Time: "+duration/1000000+ "  ms");

    }
    public void loadData()
    {
        data = "file:src/main/resources/SWRLRule/5000.owl";
        model = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);
        model.read(data);

    }

    @Override
    public void runServiceDependence() {

    }

    @Override
    public void runConnectivityDependence() {

    }

    @Override
    public void runEnvDependence() {

    }

    @Override
    public void runAppStateDependence() {

    }

    @Override
    public void runAppDataDependence() {

    }



    public  void printPropertyValues(final Individual ind, final Property prop)
    {
        final ExtendedIterator<RDFNode> rsc = ind.listPropertyValues(prop);
        cpt=cpt+ind.listPropertyValues(prop).toList().size();
         if(ind.listPropertyValues(prop).hasNext()) {
            System.out.print(ind.getLocalName() +" " +prop.getLocalName() + ": ");

            @SuppressWarnings("rawtypes")
            final ExtendedIterator<? extends Resource> rsc2 = (ExtendedIterator) rsc; //  Resource extends RDFNode
            printIterator(rsc2);
        }
    }

    public static void printIterator(final ExtendedIterator<? extends Resource> i)
    {

        while (i.hasNext())
        {
            final Resource val = i.next();
            System.out.print(val.getLocalName());
            if (i.hasNext()) System.out.print(", ");
        }
        System.out.println();
    }
    public void infer ()
    {
        final ObjectProperty serviceDependency = model.getObjectProperty("http://www.semanticweb.org/OrangeLab/ontologies/2021/9/IoTD#hasServiceDependencyTo");
        final ObjectProperty EnvDependency = model.getObjectProperty("http://www.semanticweb.org/OrangeLab/ontologies/2021/9/IoTD#hasEnvironmentDependencyTo");
        final ObjectProperty connectivityDependency = model.getObjectProperty("http://www.semanticweb.org/OrangeLab/ontologies/2021/9/IoTD#hasConnectivityDependency");
        final ObjectProperty applicationStateDependency = model.getObjectProperty("http://www.semanticweb.org/OrangeLab/ontologies/2021/9/IoTD#hasStateDependencyTo");
        final ObjectProperty applicationDataDependency = model.getObjectProperty("http://www.semanticweb.org/OrangeLab/ontologies/2021/9/IoTD#hasApplicationDataDependency");


        OntClass thisClass = model.getOntClass("https://saref.etsi.org/core/Device");
        //System.out.println("Found class: " + thisClass.toString());

        ExtendedIterator instances = thisClass.listInstances();

        while (instances.hasNext())
        {
            Individual thisInstance = (Individual) instances.next();
            printPropertyValues(thisInstance, serviceDependency);
            printPropertyValues(thisInstance, connectivityDependency);
            printPropertyValues(thisInstance, EnvDependency);
            printPropertyValues(thisInstance, applicationDataDependency);
            printPropertyValues(thisInstance,applicationStateDependency);
        }
        System.out.println(cpt);
    }

}
