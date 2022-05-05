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

import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.FunctionBase2;

public class StrictStringMatching extends FunctionBase2
{
    public NodeValue exec(NodeValue value1, NodeValue value2){
       // System.out.println(value1.asString()+"   "+value2.asString()+"   "+value1.asString().equals(value2.asString()));
        if(value1.asString().equals(value2.asString()))  return NodeValue.makeDouble(1);
        else return NodeValue.makeDouble(0);

    }
}
