package de.tunetown.nnpg.model.neuroph;

import org.neuroph.core.transfer.Gaussian;
import org.neuroph.core.transfer.Linear;
import org.neuroph.core.transfer.Log;
import org.neuroph.core.transfer.Ramp;
import org.neuroph.core.transfer.Sgn;
import org.neuroph.core.transfer.Sigmoid;
import org.neuroph.core.transfer.Sin;
import org.neuroph.core.transfer.Step;
import org.neuroph.core.transfer.Tanh;
import org.neuroph.core.transfer.Trapezoid;

import de.tunetown.nnpg.model.neuroph.bugfixes.TanhJafama;

public enum TransferFunctionTypeExt {
	LINEAR("Linear"),
	RAMP("Ramp"),
	STEP("Step"),
	SIGMOID("Sigmoid"),
	TANH("Tanh"),
	TANH_JAFAMA("Tanh Jafama"),
	GAUSSIAN("Gaussian"),
	TRAPEZOID("Trapezoid"),
	SGN("Sgn"), 
    SIN("Sin"), 
    LOG("Log");

	private String typeLabel;
	
	private TransferFunctionTypeExt(String typeLabel) {
		this.typeLabel = typeLabel;
	}
	
	public String getTypeLabel() {
		return typeLabel;
	}

    @SuppressWarnings("rawtypes")
	public Class getTypeClass() {
        switch (this) {
        case LINEAR:
           	return Linear.class;
		case STEP:
			return Step.class;
		case RAMP:
			return Ramp.class;
		case SIGMOID:
			return Sigmoid.class;
		case TANH:
			return Tanh.class;
		case TANH_JAFAMA:
			return TanhJafama.class;
		case TRAPEZOID:
			return Trapezoid.class;
		case GAUSSIAN:
			return Gaussian.class;
		case SGN:
			return Sgn.class; 
		case SIN:
			return Sin.class;                     
		case LOG:
			return Log.class;                     
		} 

        return null;

    }
}

