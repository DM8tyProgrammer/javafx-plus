package javafx.plus.component.textfield;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MaskedTextField extends TextField {

    private static final Character DEFAULT_MASK_CHARACTER = Character.valueOf('_');
    private static final String DEFAULT_MASK_PATTERN = "";

    private StringProperty maskPattern;
    private String masked;
    private Character maskCharacter;
    private Map<Character, Pattern> patterns;

    public MaskedTextField() {
        this(DEFAULT_MASK_PATTERN, DEFAULT_MASK_CHARACTER);
    }

    public MaskedTextField(String maskPattern) {
        this(maskPattern, DEFAULT_MASK_CHARACTER);
    }

    public MaskedTextField(String maskPattern, Character maskCharacter) {

        this.maskPattern = new SimpleStringProperty(maskPattern);
        this.maskCharacter = maskCharacter;

        this.patterns = new HashMap<>();

        this.patterns.put(Character.valueOf('9'), Pattern.compile("[0-9]"));
        this.patterns.put(Character.valueOf('a'), Pattern.compile("[a-zA-Z]"));
        this.patterns.put(Character.valueOf('*'), Pattern.compile("."));

        init();
    }

    protected void init() {

        super.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                super.deselect();
                String text = super.getText();
                int index = text.indexOf(this.maskCharacter);
                if (index != -1) {
                    super.positionCaret(index);
                }
            }

        });

        this.masked = masked();
        super.setText(this.masked());
    }

    private String masked() {
        int firstMasked = -1;
        StringBuilder builder = new StringBuilder();
        char[] characters = this.maskPattern.get().toCharArray();
        for (int i = 0; i < characters.length; i++ ) {
            if(this.patterns.containsKey(Character.valueOf(characters[i]))) {
                builder.append(this.maskCharacter);
                if(firstMasked == -1) {
                    firstMasked = i;
                }
            } else {
                builder.append(characters[i]);
            }
        }
        return builder.toString();
    }


    @Override
    public void replaceText(int start, int end, String text) {


        String maskPattern = this.maskPattern.get();
        int upperBound = maskPattern.length();
        if (start < upperBound && end <= upperBound && start == end ) {
            // find pattern at typed position
            Pattern p = this.patterns.get(maskPattern.charAt(start));
            if (null != p && p.matcher(text).matches()) {

                // check next character till not any pattern
                int temp = start +1;
                StringBuilder stringBuilder = new StringBuilder(text);
                while (temp < upperBound) {
                    char c = maskPattern.charAt(temp);
                    Pattern p1 =  this.patterns.get(c);
                    if (null != p1) {
                        break;
                    } else {
                        stringBuilder.append(c);
                    }
                    temp++;
                }

                super.replaceText(start, end+stringBuilder.length(), stringBuilder.toString());
            }
        } else if (start < upperBound && end <= upperBound && start < end) {

            // backspace
            super.replaceText(start, end, this.masked.charAt(start)+"");
            int temp = start - 1;

            int count = 0;
            while (temp >= 0) {
                char c = maskPattern.charAt(temp);
                Pattern p1 =  this.patterns.get(c);
                if (null != p1) {
                    break;
                } else {
                    count++;
                }
                temp--;

            }
            super.positionCaret(start-count);
        }

    }


}
