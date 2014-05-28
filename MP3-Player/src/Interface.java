/**
 * Created by student on 26-5-14.
 */
public class Interface {
    private General_IO io;

    public Interface(General_IO io) {
        this.io = io;
    }

    public boolean debounce(int pin) {
        boolean result[] = new boolean[3];
        for (int i = 0; i < 3; i++) {
            result[i] = Gpio.ioread(pin) == 1;

            Util.sleep(0, 12);
        }
        for (boolean res : result) {
            // als het resultaat false is, dan false returnen, anders doorchecken.
            // als ze alledrie niet false zijn dan returnt deze functie true
            if (!res) {
                return res;
            }
        }
        return true;
    }

    public boolean readButtonLeft() {
        System.out.println("button left is: " + (Gpio.ioread(Pins.DrukknopL.getNumber()) == 1));
        return debounce(Pins.DrukknopL.getNumber());
    }

    public boolean readButtonRight() {
        return debounce(Pins.DrukknopR.getNumber());
    }

    /**
     * 0 = left
     * 1 = nothing
     * 2 = right
     * -1 = ERRORERINO
     *
     * @return int
     */
    public int readRotaryDial() {
        // 0 = left, 1 = nothing, 2 = right, -1 = error
        // ???
        // ___|-------|_____   :A
        // ______|-------|____ :B
        // Linksom A eerst, B tweede
        //
        // ______|-------|____ :A
        // ___|-------|_____   :B
        // Rechtsom B eerst, A tweede
        boolean[] A = new boolean[3];
        boolean[] B = new boolean[3];
        for (int i = 0; i < 3; i++) {
            A[i] = debounce(Pins.RDA.getNumber());
            B[i] = debounce(Pins.RDB.getNumber());
            // TODO: Test dis shi-it
            Util.sleep(0, 40);
        }
        for (int i = 0; i < 3; i++) {
            if (A[i] != B[i]) {
                if (A[i]) return 0;
                if (B[i]) return 2;
            }
        }
        return 1;
    }

    public boolean readButtonMiddle() {
        return debounce(Pins.RDA.getNumber()) && debounce(Pins.RDB.getNumber());
    }


}
