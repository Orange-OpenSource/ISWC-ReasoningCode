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

import info.debatty.java.stringsimilarity.JaroWinkler;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.FunctionBase2;

public class JaroSimilarity extends FunctionBase2
{
    public NodeValue exec(NodeValue value1, NodeValue value2){
         JaroWinkler ls = new JaroWinkler();
        double i = ls.similarity(value1.asString(), value2.asString());
         return NodeValue.makeDouble(i);
    }
}