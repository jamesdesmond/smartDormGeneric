import se.hirt.pi.adafruitlcd.Button;
import se.hirt.pi.adafruitlcd.ILCD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by james on 10/12/16.
 */
/*
 *Tip for having multiple possible recipients:
 * In Runners LCDApps[] add a separate entry for each person, e.g. new sendText(Configuration.People.DEFAULT)
 */
public class sendText implements LCDApps {
    private int currentMessageMenu;
    private int currentContactMenu;
    private Person person;
    private static ArrayList<String> messages;
    private static ArrayList<Person> peopleArrayList;
    private boolean inSelectContact = true;
    public sendText() {
        ConfigurationEnums.readConfigurationFile();
        this.person = new Person("DEFAULT","DEFAULT");
        currentMessageMenu = 0;
        currentContactMenu = 0;
        messages = ConfigurationEnums.messageArrayList;
        peopleArrayList = ConfigurationEnums.peopleArrayList;
        System.out.println(Arrays.asList("xxx" + peopleArrayList));
        System.out.println(peopleArrayList.get(0).getName());
    }

    private void sendText(String address, String message) {
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
        return "Texting";
    }

    private void menu(ILCD ilcd, Button button)throws IOException {
        try {
            if (inSelectContact) {
                switch (button) {
                    case RIGHT:
                        currentContactMenu++;
                        currentContactMenu = (currentContactMenu > peopleArrayList.size() -1) ? 0 : currentContactMenu;
                        ilcd.clear();
                        System.out.println("RIGHTCASE: " + peopleArrayList.get(currentContactMenu).getName() + ":" + peopleArrayList.get(currentContactMenu).getEmail());
                        ilcd.setText(peopleArrayList.get(currentContactMenu).getName());
                        break;
                    case LEFT:
                        currentContactMenu--;
                        currentContactMenu = (currentContactMenu < 0) ? peopleArrayList.size() -1 : currentContactMenu;
                        ilcd.clear();
                        ilcd.setText(peopleArrayList.get(currentContactMenu).getName());
                        break;
                    case SELECT:
                        inSelectContact = false;
                        person = peopleArrayList.get(currentContactMenu);
                        ilcd.clear();
                        ilcd.setText(messages.get(currentMessageMenu));
                        //ilcd.setText(messages[currentMessageMenu]);
                        break;
                }
            } else {
                switch (button) {
                    case RIGHT:
                        currentMessageMenu++;
                        currentMessageMenu = (currentMessageMenu > messages.size() - 1) ? 0 : currentMessageMenu;
                        ilcd.clear();
                        //ilcd.setText(messages[currentMessageMenu]);
                        ilcd.setText(messages.get(currentMessageMenu));
                        break;
                    case LEFT:
                        currentMessageMenu--;
                        currentMessageMenu = (currentMessageMenu < 0) ? messages.size() - 1 : currentMessageMenu;
                        ilcd.clear();
                        ilcd.setText(messages.get(currentMessageMenu));
                        break;
                    case SELECT:
                        ilcd.clear();
                        ilcd.setText("Loading...");
                        sendText(person.getEmail(), messages.get(currentMessageMenu));
                        ilcd.clear();
                        ilcd.setText("Sent!");
                        inSelectContact = true;
                        currentContactMenu = 0;
                        currentMessageMenu = 0;
                        break;
                }
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
        ilcd.setText(peopleArrayList.get(currentContactMenu).getName());
    }

}
