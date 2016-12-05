import se.hirt.pi.adafruitlcd.Button;
import se.hirt.pi.adafruitlcd.ILCD;

import java.io.IOException;
import java.util.ArrayList;

/***
 * **********************
 * Description
 * **********************
 *
 * Sends text messages through mutt and sms email gateways
 *
 * **********************
 * Analysis
 * **********************
 *
 * Inputs: Contacts from Configuration.ini
 * Outputs: LCD,Emails
 *
 * **********************
 * Pseudocode
 * **********************
 *
 * Select person
 * Select message
 * combine person + message into mutt command
 * execute mutt command
 *
 * @author james desmond
 */
public class sendText implements LCDApps {
    private int currentMessageMenu;
    private int currentContactMenu;
    private Person person;
    private static ArrayList<String> messages;
    private static ArrayList<Person> peopleArrayList;
    private boolean inSelectContact = true;

    /**
     * Constructor, sets up fields to correct values
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

    /**
     * Gets Name
     * @return name
     */
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

    /**
     * Passes buttonpresses through to menu()
     * @param ilcd LCD
     * @param button used for reading button presses
     * @throws IOException
     */
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
