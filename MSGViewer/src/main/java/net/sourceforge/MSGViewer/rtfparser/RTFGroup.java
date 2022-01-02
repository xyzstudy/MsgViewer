package net.sourceforge.MSGViewer.rtfparser;

import java.nio.charset.Charset;

public class RTFGroup {

    private final StringBuilder text_content = new StringBuilder();
    private final Charset characterSet;
    private String destination = "";
    private String command = "";

    public RTFGroup(Charset characterSet) {
        this.characterSet = characterSet;
    }

    public void handleCommand(String command) {
        this.command = command;
    }

    public void destination(String destination) {
        this.destination = destination;
    }

    public String getTextContent() {
        return text_content.toString();
    }

    public void addEscapedChar(String escapedChar) {
        int hexa = Integer.parseInt(escapedChar.substring(2), 16);
        byte[] bytes = {(byte) hexa};
        addTextContent(new String(bytes, characterSet));
    }

    public void addUnicodeChar(String code) {
        String codepoint = code.substring(2);
        char ch = (char) Integer.parseInt(codepoint);
        addCharacterContent(ch);
    }

    public void addTextContent(String text) {
        if (destination.startsWith("\\htmltag") || command.equals("\\htmlrtf0"))
            text_content.append(text);
    }

    private void addCharacterContent(char character) {
        if (destination.startsWith("\\htmltag") || command.equals("\\htmlrtf0"))
            text_content.append(character);
    }
}
