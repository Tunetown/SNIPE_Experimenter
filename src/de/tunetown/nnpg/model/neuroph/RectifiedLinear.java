/**
 * Copyright 2010 Neuroph Project http://neuroph.sourceforge.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.tunetown.nnpg.model.neuroph;

import java.io.Serializable;
import org.neuroph.core.transfer.TransferFunction;

/**
 * <pre>
 * ReLU neuron transfer function.
 * </pre>
 *
 * @author Thomas Weber
 */
public class RectifiedLinear extends TransferFunction implements Serializable {
    private static final long serialVersionUID = 2L;

    private double negativeScaling = 0.1;    

    public RectifiedLinear() {
    }
    
    public RectifiedLinear(double negativeScaling) {
    	this.negativeScaling = negativeScaling;
    }

    @Override
    final public double getOutput(double input) {
    	if(input > 0) return input;
		else return negativeScaling * input;
    }

    @Override
    final public double getDerivative(double net) {
    	if(net > 0) return 1;
		else return negativeScaling;
    }
}
