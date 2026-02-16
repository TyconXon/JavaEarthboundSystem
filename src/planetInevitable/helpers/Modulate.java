package planetInevitable.helpers;

import java.util.function.UnaryOperator;

public class Modulate {

    public UnaryOperator<Float> mod;

    public Object mod(float i) {
        return this.mod.apply(i);
    }

    public enum types {MULT, ADD, POW, COMPLEX}

    types type;
    float value;

    public Modulate(types type, float value) {
        this.type = type;
        this.value = value;
        if (type == types.MULT) {
            this.mod = (Float in) -> {
                return in * value;
            };
        } else if (type == types.ADD) {
            this.mod = (Float in) -> {
                return in + value;
            };
        } else if (type == types.POW) {
            this.mod = (Float in) -> {
                return (float) Math.pow(in, value);
            };
        }
    }

    Modulate(UnaryOperator<Float> lamb) {
        this.type = types.COMPLEX;
        this.value = 0;
        this.mod = lamb;
    }

    public Modulate() {
        this.type = types.ADD;
        this.value = 0;
        this.mod = (Float in) -> {
            return in;
        };
    }
}
