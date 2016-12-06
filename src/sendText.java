import se.hirt.pi.adafruitlcd.Button;
import se.hirt.pi.adafruitlcd.ILCD;

import java.io.IOException;
import java.util.ArrayList;

/***
 * @author James Desmond
 * SmartDormGeneric Final Assignment
 * CS1000-Fall 2016
 * Due: 12/6/16
 *
 * **********************
 * Description
 * **********************
 *
 * Sends text messages through mutt and SMS email gateways
 *
 */
public class sendText implements LCDApps {
    /**
     * Tracks current message being displayed
     */
    private int currentMessageMenu;
    /**
     * Tracks current contact being diplsayed
     */
    private int currentContactMenu;
    /**
     * Person to be sent a message
     */
    private Person person;
    /**
     * ArrayList of message options
     */
    private static ArrayList<String> messages;
    /**
     * ArrayList of contact options
     */
    private static ArrayList<Person> peopleArrayList;
    /**
     * True if the select contact menu is visible
     */
    private boolean inSelectContact = true;

    /**
     * Default Constructor, Reads Configuration.ini, sets person to a default person, sets currentMessageMenu to 0,
     * sets currentContactMenu to 0, sets messages to the message choices in Configuration, sets peopleArrayList to the contact choices in Configuration
     */
    public sendText() {
        ConfigurationEnums.readConfigurationFile();
        this.person = new Person("DEFAULT","DEFAULT");
        currentMessageMenu = 0;
        currentContactMenu = 0;
        messages = ConfigurationEnums.messageArrayList;
        peopleArrayList = ConfigurationEnums.peopleArrayList;
    }

    /**
     * Sends emails through mutt
     * @param address email address to be sent to
     * @param message message to send with the email
     */
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

    /**
     * Displays menu for SendText, used for selecting people and messages
     * @param ilcd LCD
     * @param button used for reading buttonpresses
     * @throws IOException
     */
    private void menu(ILCD ilcd, Button button)throws IOException {
        try {
            if (inSelectContact) {
                switch (button) {
                    case RIGHT:
                        currentContactMenu++;
                        currentContactMenu = (currentContactMenu > peopleArrayList.size() -1) ? 0 : currentContactMenu;
                        ilcd.clear();
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

    /**
     * Used for the initial sendText.run()
     * @param ilcd LCD
     * @throws IOException
     */
    public void run (ILCD ilcd) throws IOException {
        ilcd.clear();
        ilcd.setText(peopleArrayList.get(currentContactMenu).getName());
    }

}
