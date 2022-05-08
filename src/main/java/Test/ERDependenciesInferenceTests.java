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

public class ERDependenciesInferenceTests {

    public static void main(final String[] args)  {
        Shacl shacl= new Shacl();



        //choose data set from Datasets directory [868, 1736, 2604, 4340] unit: number of triples

        shacl.setDataSet("4340");
        shacl.executeERDep();










    }
}
