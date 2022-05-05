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

import openllet.owlapi.OWL;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import java.io.File;

public class OwlApiHermit  extends Swrl{
    private OWLReasoner reasoner;
    private OWLOntology ontology;
    private String data;
    @Override
    public void run() {
        data = "src/main/resources/SWRLRule/IoTDswrl.owl";
        long startTime, endTime, duration;

        // create an ontology manager
        final OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

        // read the ontology

        try {
            // load the ontology to the  Hermit reasoner
            startTime=System.nanoTime();
            ontology = manager.loadOntologyFromOntologyDocument(new File(data));
            OWLReasonerFactory reasonerFactory = new ReasonerFactory();
            reasoner = reasonerFactory.createReasoner(ontology);
            endTime = System.nanoTime();

            duration = (endTime - startTime);
            System.out.println("Swrl owl api hermit  data load Time: "+duration/1000000+ "  ms");
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }

        startTime=System.nanoTime();
        infer();
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Swrl owl api hermit  inference Time: "+duration/1000000+ "  ms");


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


    @Override
    public void loadData() {
        data = "DataSets/50.owl";

        // create an ontology manager
        final OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

        // read the ontology

        try {
            // load the ontology to the  Hermit reasoner
             ontology = manager.loadOntologyFromOntologyDocument(new File(data));
            OWLReasonerFactory reasonerFactory = new ReasonerFactory();
            reasoner = reasonerFactory.createReasoner(ontology);

        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
    }

    public void infer()
    {
        final OWLClass Device = OWL.Class("https://saref.etsi.org/core/Device");
        final OWLObjectProperty serviceDependency = OWL.ObjectProperty("http://www.semanticweb.org/OrangeLab/ontologies/2021/9/IoTD#hasServiceDependencyTo");
        final OWLObjectProperty connectivityDependency = OWL.ObjectProperty("http://www.semanticweb.org/OrangeLab/ontologies/2021/9/IoTD#hasConnectivityDependency");
        final OWLObjectProperty envDependency = OWL.ObjectProperty("http://www.semanticweb.org/OrangeLab/ontologies/2021/9/IoTD#hasEnvironmentDependencyTo");
        final OWLObjectProperty StateDependency = OWL.ObjectProperty("http://www.semanticweb.org/OrangeLab/ontologies/2021/9/IoTD#hasStateDependencyTo");
        final OWLObjectProperty applicationDependency = OWL.ObjectProperty("http://www.semanticweb.org/OrangeLab/ontologies/2021/9/IoTD#hasApplicationDataDependency");

        // get all instances of IoTDevice class
        final NodeSet<OWLNamedIndividual> individuals = reasoner.getInstances(Device, false);
        for (final Node<OWLNamedIndividual> sameInd : individuals)
        {
            // sameInd contains information about the _individual (and all other individuals that were inferred to be the same)
            final OWLNamedIndividual ind = sameInd.getRepresentativeElement();
            System.out.print(ind.getIRI());
            // get the info about this specific _individual

            final NodeSet<OWLNamedIndividual> servdependencies = reasoner.getObjectPropertyValues(ind, serviceDependency);
            final NodeSet<OWLNamedIndividual> conndependencies = reasoner.getObjectPropertyValues(ind, connectivityDependency);
            final NodeSet<OWLNamedIndividual> envdependencies = reasoner.getObjectPropertyValues(ind, envDependency);
            final NodeSet<OWLNamedIndividual> statedependencies = reasoner.getObjectPropertyValues(ind, StateDependency);
            final NodeSet<OWLNamedIndividual> appdependencies = reasoner.getObjectPropertyValues(ind, applicationDependency);



            if (!servdependencies.isEmpty())
            {
                System.out.print(" has service dependency to  ");
                for (final Node<OWLNamedIndividual> dep : servdependencies)
                    System.out.print(" " + dep.getRepresentativeElement().getIRI());
            }
            if (!conndependencies.isEmpty())
            {
                System.out.print(" has connectivity dependency to  ");
                for (final Node<OWLNamedIndividual> dep : conndependencies)
                    System.out.print(" " + dep.getRepresentativeElement().getIRI());
            }
            if (!envdependencies.isEmpty())
            {
                System.out.print(" has environment based dependency to  ");
                for (final Node<OWLNamedIndividual> dep : envdependencies)
                    System.out.print(" " + dep.getRepresentativeElement().getIRI());
            }

            if (!statedependencies.isEmpty())
            {
                System.out.print(" has state dependency to  ");
                for (final Node<OWLNamedIndividual> dep : statedependencies)
                    System.out.print(" " + dep.getRepresentativeElement().getIRI());
            }

            if (!appdependencies.isEmpty())
            {
                System.out.print(" has application dependency to  ");
                for (final Node<OWLNamedIndividual> dep : appdependencies)
                    System.out.print(" " + dep.getRepresentativeElement().getIRI());
            }
            System.out.println();
            System.out.println();
        }

    }

}
