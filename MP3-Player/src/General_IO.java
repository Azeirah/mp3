public class General_IO extends Gpio {
    public General_IO() {
        super();
    }

    public void writeBufferedLCD(int _A0, byte _l) {
        for (int i = 0; i < 8; i++) {
            writeLCD(_A0, (_l >> i & 1) == 1);
        }
    }

    public void writeLCD(int _A0, boolean _l) {
        setPin(LCD.A0, _A0);
        setPin(LCD.MOSI, _l);
        setPin(LCD.SCL, true);
        setPin(LCD.SCL, false);
    }

    public void setPin(int pin, int pinState) {
        iowrite(pin, pinState);
    }

    public void setPin(int pin, boolean pinState) {
        iowrite(pin, (pinState) ? 1 : 0);
    }

    public boolean readPin(int _N) {
        boolean _value = false;
        if (ioread(_N) > 0) {
            _value = true;
        }
        return _value;
    }
}