import se.hirt.pi.adafruitlcd.Button;
import se.hirt.pi.adafruitlcd.ILCD;

import java.io.IOException;

/**
 * Created by james on 10/12/16.
 */
/*
 *Tip for having multiple possible recipients:
 * In Runners LCDApps[] add a separate entry for each person, e.g. new sendText(Configuration.People.DEFAULT)
 */
public class sendText implements LCDApps {
    private int currentMenu;
    private Person person;
    private static final String[] messages = {"Message\nOptions","Second\nScreen"}; //Remember that its a 16x2 display
    public sendText() {
        this.person = new Person("DEFAULT","DEFAULT");
        currentMenu = -1;
    }

    public sendText(Person person) {
        this.person = person;
        currentMenu = -1;
    };

    private  void sendText(String address, String message) {
        String[] command = {"/bin/bash", "-c", "mutt -F /root/.muttrc -s \"SmartDorm\" " + address + " <<< \"" + message + "\""};
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        try {
            Process process = processBuilder.start();
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "Text " + person;
    }

    private void menu(ILCD ilcd, Button button)throws IOException {
        try {
            switch (button) {
                case RIGHT:
                    currentMenu++;
                    currentMenu = (currentMenu > messages.length - 1) ? 0 : currentMenu;
                    ilcd.clear();
                    ilcd.setText(messages[currentMenu]);
                    break;
                case LEFT:
                    currentMenu--;
                    currentMenu = (currentMenu < 0) ? messages.length - 1 : currentMenu;
                    ilcd.clear();
                    ilcd.setText(messages[currentMenu]);
                    break;
                case SELECT:
                    ilcd.clear();
                    ilcd.setText("Loading...");
                    sendText(person.getEmail(), messages[currentMenu]);
                    ilcd.clear();
                    ilcd.setText("Sent!");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run (ILCD ilcd, Button button) throws IOException {
        menu(ilcd , button);
    }
    public void run (ILCD ilcd) throws IOException {
        ilcd.clear();
        ilcd.setText("Use Right & Left\nto select msg");
    }

}
