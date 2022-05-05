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

import org.apache.commons.io.IOUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.sparql.function.FunctionRegistry;
import org.topbraid.shacl.rules.RuleUtil;
import org.topbraid.shacl.util.ModelPrinter;
import org.topbraid.spin.util.JenaUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Shacl extends Reasoner {

    private Model inferenceModel;
    private String data;
    private String ont;
    private String shapeserv ;
    private String shapeconn ;
    private String shapecon ;
    private String shapeenv ;
    private String shapeappdata ;
    private String shapeappstate ;
    private String shapeERHigh ;
    private String shapeERLow ;
    private String shapeERService ;


    private Model dataModel ;
    private Model shapeModel;
    private String  service ;
    private String  connectivity;
    private String  connectivityToconnectivity;
    private String  environment;

    private String  applicationState;
    private String  applicationData;
    private Path path = Paths.get(".").toAbsolutePath().normalize();

    public Shacl()
    {



        ont="file:" + path.toFile().getAbsolutePath() + "/src/main/resources/ShaclRule/ont.ttl";

        shapeserv = "file:" + path.toFile().getAbsolutePath() + "/src/main/resources/ShaclRule/ERDependencies/ServiceRule.ttl";
        shapeconn = "file:" + path.toFile().getAbsolutePath() + "/src/main/resources/ShaclRule/ERDependencies/ConnectivityRule.ttl";
        shapeenv = "file:" + path.toFile().getAbsolutePath() + "/src/main/resources/ShaclRule/ERDependencies/EnvDependencyRule.ttl";
        shapeappdata = "file:" + path.toFile().getAbsolutePath() + "/src/main/resources/ShaclRule/ERDependencies/ApplicationDependencyDataRule.ttl";
        shapeappstate = "file:" + path.toFile().getAbsolutePath() + "/src/main/resources/ShaclRule/ERDependencies/ApplicationDependencyStateRule.ttl";
        shapecon = "file:" + path.toFile().getAbsolutePath() + "/src/main/resources/ShaclRule/ERDependencies/ConnectivityToConnectivityRule.ttl";
        shapeERLow = "file:" + path.toFile().getAbsolutePath() + "/src/main/resources/ShaclRule/ERDependencies/ERDevice.ttl";
        shapeERService="file:" + path.toFile().getAbsolutePath() + "/src/main/resources/ShaclRule/ERDependencies/ERService.ttl";

        service = "file:" + path.toFile().getAbsolutePath() + "/src/main/resources/ShaclRule/DependenciesRule/ServiceRule.ttl";
        connectivity = "file:" + path.toFile().getAbsolutePath() + "/src/main/resources/ShaclRule/DependenciesRule/ConnectivityRule.ttl";
        environment = "file:" + path.toFile().getAbsolutePath() + "/src/main/resources/ShaclRule/DependenciesRule/EnvDependencyRule.ttl";
        applicationData= "file:" + path.toFile().getAbsolutePath() + "/src/main/resources/ShaclRule/DependenciesRule/ApplicationDependencyDataRule.ttl";
        applicationState= "file:" + path.toFile().getAbsolutePath() + "/src/main/resources/ShaclRule/DependenciesRule/ApplicationDependencyStateRule.ttl";
        connectivityToconnectivity= "file:" + path.toFile().getAbsolutePath() + "/src/main/resources/ShaclRule/DependenciesRule/ConnectivityToConnectivityRule.ttl";


    }

   public void setDataSet(String datasetName)
    {
        data = "file:" + path.toFile().getAbsolutePath() + "/src/main/resources/DataSets/"+datasetName+".ttl";

    }

    @Override
    public void run ()
    {
        long startTime = System.nanoTime();
        Model dataModel = JenaUtil.createDefaultModel();
        Model shapeModel = JenaUtil.createDefaultModel();
        dataModel.read(ont);
        dataModel.read(data);
        shapeModel.read(shapeserv);
        shapeModel.read(shapeconn);
        shapeModel.read(shapeenv);
        shapeModel.read(shapeappdata);
        shapeModel.read(shapeappstate);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Shacl load data execution Time: "+duration/1000000+ "  ms");
        System.out.println("--------------------------------------------------");

        inferenceModel = JenaUtil.createDefaultModel();
        startTime = System.nanoTime();
        inferenceModel = RuleUtil.executeRules(dataModel, shapeModel, inferenceModel, null);
        System.out.println("Dependencies Inference results : ");
        printInferenceResult();
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Shacl TopBraid inference Time: "+duration/1000000+ "  ms");

    }
    public void loadData()
    {
        dataModel = JenaUtil.createDefaultModel();
        shapeModel = JenaUtil.createDefaultModel();
        dataModel.read(ont);
        dataModel.read(data);
        shapeModel.read(service);
        shapeModel.read(connectivity);
        shapeModel.read(environment);
        shapeModel.read(connectivityToconnectivity);
        shapeModel.read(applicationData);
        shapeModel.read(applicationState);
    }

    public void infer()
    {
        inferenceModel = JenaUtil.createDefaultModel();
        inferenceModel = RuleUtil.executeRules(dataModel, shapeModel, inferenceModel, null);
        System.out.println("Dependencies Inference results : ");
        printInferenceResult();
     }
    public void printInferenceResult ()
    {
        System.out.println(ModelPrinter.get().print(inferenceModel));
        System.out.println(inferenceModel.listStatements().toList().size());
    }

    public void runServiceDependence()
    {

        long startTime = System.nanoTime();
        Model dataModel = JenaUtil.createDefaultModel();
        Model shapeModel = JenaUtil.createDefaultModel();
        dataModel.read(ont);
        dataModel.read(data);
        shapeModel.read(shapeserv);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Shacl load data execution Time: "+duration/1000000+ "  ms");
        System.out.println("--------------------------------------------------");

        inferenceModel = JenaUtil.createDefaultModel();
        startTime = System.nanoTime();
        inferenceModel = RuleUtil.executeRules(dataModel, shapeModel, inferenceModel, null);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Shacl TopBraid inference Time: "+duration/1000000+ "  ms");
        System.out.println("Dependencies Inference results : ");
        printInferenceResult();

    }

    @Override
    public void runConnectivityDependence() {

        long startTime = System.nanoTime();
        Model dataModel = JenaUtil.createDefaultModel();
        Model shapeModel = JenaUtil.createDefaultModel();
        dataModel.read(ont);
        dataModel.read(data);
        shapeModel.read(shapeconn);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Shacl load data execution Time: "+duration/1000000+ "  ms");
        System.out.println("--------------------------------------------------");

        inferenceModel = JenaUtil.createDefaultModel();
        startTime = System.nanoTime();
        inferenceModel = RuleUtil.executeRules(dataModel, shapeModel, inferenceModel, null);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Shacl TopBraid inference Time: "+duration/1000000+ "  ms");
        System.out.println("Dependencies Inference results : ");
        printInferenceResult();
    }

    @Override
    public void runEnvDependence() {
        long startTime = System.nanoTime();
        Model dataModel = JenaUtil.createDefaultModel();
        Model shapeModel = JenaUtil.createDefaultModel();
        dataModel.read(ont);
        dataModel.read(data);
        shapeModel.read(shapeenv);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Shacl load data execution Time: "+duration/1000000+ "  ms");
        System.out.println("--------------------------------------------------");

        inferenceModel = JenaUtil.createDefaultModel();
        startTime = System.nanoTime();
        inferenceModel = RuleUtil.executeRules(dataModel, shapeModel, inferenceModel, null);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Shacl TopBraid inference Time: "+duration/1000000+ "  ms");
        System.out.println("Dependencies Inference results : ");
        printInferenceResult();
    }

    @Override
    public void runAppStateDependence() {
        long startTime = System.nanoTime();
        Model dataModel = JenaUtil.createDefaultModel();
        Model shapeModel = JenaUtil.createDefaultModel();
        dataModel.read(ont);
        dataModel.read(data);
        shapeModel.read(shapeappstate);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Shacl load data execution Time: "+duration/1000000+ "  ms");
        System.out.println("--------------------------------------------------");

        inferenceModel = JenaUtil.createDefaultModel();
        startTime = System.nanoTime();
        inferenceModel = RuleUtil.executeRules(dataModel, shapeModel, inferenceModel, null);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Shacl TopBraid inference Time: "+duration/1000000+ "  ms");
        System.out.println("Dependencies Inference results : ");
        printInferenceResult();

    }

    @Override
    public void runAppDataDependence() {
        long startTime = System.nanoTime();
        Model dataModel = JenaUtil.createDefaultModel();
        Model shapeModel = JenaUtil.createDefaultModel();
        dataModel.read(ont);
        dataModel.read(data);
        shapeModel.read(shapeappdata);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Shacl load data execution Time: "+duration/1000000+ "  ms");
        System.out.println("--------------------------------------------------");

        inferenceModel = JenaUtil.createDefaultModel();
        startTime = System.nanoTime();
        inferenceModel = RuleUtil.executeRules(dataModel, shapeModel, inferenceModel, null);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Shacl TopBraid inference Time: "+duration/1000000+ "  ms");
        System.out.println("Dependencies Inference results : ");
        printInferenceResult();

    }


    public void ERDevice(String data1)
    {
        dataModel = JenaUtil.createDefaultModel();
        shapeModel = JenaUtil.createDefaultModel();
        String functionUri1 = "http://www.example.org/JaroSimilarity";
        FunctionRegistry.get().put(functionUri1 , JaroSimilarity.class);
        String functionUri2 = "http://www.example.org/StrictStringFunction";
        FunctionRegistry.get().put(functionUri2 , StrictStringMatching.class);
        dataModel.read(ont);
        dataModel.read(data);
        shapeModel.read(shapeERLow);


    }

    public void ERService(String data)
    {
        dataModel = JenaUtil.createDefaultModel();
        shapeModel = JenaUtil.createDefaultModel();
        String functionUri1 = "http://www.example.org/JaroSimilarity";
        FunctionRegistry.get().put(functionUri1 , JaroSimilarity.class);
        String functionUri2 = "http://www.example.org/StrictStringFunction";
        FunctionRegistry.get().put(functionUri2 , StrictStringMatching.class);
        dataModel.read(ont);
        try
        {
            dataModel.read(IOUtils.toInputStream(data, "UTF-8"), null, "TURTLE");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        shapeModel.read(shapeERService);

    }
    public void executeERDep()
    {
        Model global =JenaUtil.createDefaultModel();
        dataModel = JenaUtil.createDefaultModel();
        shapeModel = JenaUtil.createDefaultModel();
        String functionUri1 = "http://www.example.org/JaroSimilarity";
        FunctionRegistry.get().put(functionUri1 , JaroSimilarity.class);
        String functionUri2 = "http://www.example.org/StrictStringFunction";
        FunctionRegistry.get().put(functionUri2 , StrictStringMatching.class);
        long startTime,endTime,duration;
        dataModel.read(ont);

        System.out.println("The used dataset is "+data);
        startTime=System.nanoTime();
        dataModel.read(data);
        shapeModel.read(shapeERLow);
        inferenceModel = JenaUtil.createDefaultModel();
        inferenceModel = RuleUtil.executeRules(dataModel, shapeModel, inferenceModel, null);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("The inferred dependencies "+ModelPrinter.get().print(inferenceModel));

        global.add(dataModel);
        global.add(inferenceModel);
        dataModel = JenaUtil.createDefaultModel();
        shapeModel = JenaUtil.createDefaultModel();
        dataModel.read(ont);

        startTime=System.nanoTime();
        dataModel.read(data);
        shapeModel.read(shapeERService);
        inferenceModel = JenaUtil.createDefaultModel();
        inferenceModel = RuleUtil.executeRules(dataModel, shapeModel, inferenceModel, null);
        endTime = System.nanoTime();
        duration = (endTime - startTime)+duration;
        System.out.println("ER execution Time: " + duration / 1000000 + "  ms");
        System.out.println("The inferred dependencies "+ModelPrinter.get().print(inferenceModel));

        global.add(inferenceModel);




        startTime=System.nanoTime();
        shapeModel = JenaUtil.createDefaultModel();
        shapeModel.read(shapeserv);
        shapeModel.read(shapeconn);
        shapeModel.read(shapecon);
        shapeModel.read(shapeenv);
        shapeModel.read(shapeappdata);
        shapeModel.read(shapeappstate);
        inferenceModel = JenaUtil.createDefaultModel();
        inferenceModel = RuleUtil.executeRules(global, shapeModel, inferenceModel, null);
        endTime = System.nanoTime();
        duration = (endTime - startTime);

        System.out.println("dependency inference execution Time: " + duration / 1000000 + "  ms");
        System.out.println("Number of inferred dependencies "+inferenceModel.listStatements().toList().size());
        System.out.println("The inferred dependencies "+ModelPrinter.get().print(inferenceModel));


    }
}
