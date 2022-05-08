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
package Test;

import Inference.*;
public class SHACLvsSWRL {

    public static void main(final String[] args)  {
        Shacl shacl= new Shacl();
        OwlAPIOpenllet owlAPIOpenllet=new OwlAPIOpenllet();
         long startTime,endTime,duration;
        System.out.println("--------------------------------------------------");

        /*NB:

        // run one reasoning call (shacl or swrl) in an execution not both! because java class charging is done in the first call
        // i.e when running swrl comment shacl and one running shacl comment swrl !

        */

        //choose data set from Datasets directory [50, 100, 200, 1000 ,2000 ,5000] unit: number of dependencies
        String dataSets="5000";


        /*owl api openllet swrl*/
        owlAPIOpenllet.setDataSets(dataSets);
        startTime = System.nanoTime();
        owlAPIOpenllet.loadData();
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        startTime = System.nanoTime();
        owlAPIOpenllet.infer();
        endTime = System.nanoTime();
        System.out.println("owl api openllet data load Time: " + duration / 1000000 + "  ms");
        duration = (endTime - startTime);
        System.out.println(" owl api openllet  inference Time: " + duration / 1000000 + "  ms");


        /*shacl*/
        shacl.setDataSet(dataSets);
        startTime = System.nanoTime();
        shacl.loadData();
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        startTime = System.nanoTime();
        shacl.infer();
        endTime = System.nanoTime();
        System.out.println("Shacl data load Time: " + duration / 1000000 + "  ms");

        duration = (endTime - startTime);
        System.out.println("Shacl inference Time: " + duration / 1000000 + "  ms");





    }
}
