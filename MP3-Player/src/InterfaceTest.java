import java.io.IOException;

public class InterfaceTest {
    public static void main(String[] args) {
        Main main = new Main();
        try {
            main.init_io();
            main.init_interface();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        // set pin19 High
//        Gpio.iowrite(Pins.Pin19.getNumber(), 1);
//
//        System.out.println("WAITING FOR INPUT");
//        // read all pins gajillion times a second
//        while (true) {
//            for (Pins pin : Pins.values()) {
//                System.out.print(Gpio.ioread(pin.getNumber()));
//            }
//            System.out.println("");
//        }

        int rotary;
        while (true) {
        	main.anInterface.read();
//            if (main.anInterface.readButtonLeft())
//                System.out.println("LEFT pressed");
//            if (main.anInterface.readButtonRight())
//                System.out.println("RIGHT pressed");
//            if (main.anInterface.readButtonMiddle())
//                System.out.println("MIDDLE pressed");
            rotary = main.anInterface.getRotaryDialSignal();
            if (rotary == 0)
                System.out.println("ROTARY DIAL ROTATING LEFT, <--");
            if (rotary == 2)
                System.out.println("ROTARY DIAL ROTATING RIGHT, -->");
        }
//        while (true) {
//        	main.anInterface.read();
//        	int rotary = main.anInterface.getRotaryDialSignal();
//        	if (rotary == 0) {
//        		System.out.println("Rotating dial left");
//        	} else if (rotary == 2) {
//        		System.out.println("Rotating dial right");
//        	}
//        	if (main.anInterface.isLeftButtonSignal()) {
//        		System.out.println("Button left pressed");
//        	}
//        	if (main.anInterface.isRightButtonSignal()) {
//        		System.out.println("Button right pressed");
//        	}
//        	if (main.anInterface.isMiddleButtonSignal()) {
//        		System.out.println("Button middle pressed");
//        	}
//        }
    }
}