package bl.common;

import java.util.ArrayList;
import java.util.List;


/**
 * Keyed Messages represent a catalog key and its corresponding replacement parameter data.
 */
public class KeyedMessage {

    /** Message key for message catalog lookup. */
    private final String messageKey;

    /**
     * Message key values are the replacement values to be substituted into the catalog string associated with the messageKey.
     */
    private final List<String> messageKeyValues;

    /**
     * Constructs a message key object given key name as string.
     * 
     * @param messageKey
     *            the key
     */
    public KeyedMessage(final String messageKey) {
        this(messageKey, new ArrayList<String>());
    }

    /**
     * Constructs a message key object given key name as string and a list of message key values to substitute.
     * 
     * @param messageKey
     *            the key
     * @param messageKeyValues
     *            the substitution values
     */
    public KeyedMessage(final String messageKey, final List<String> messageKeyValues) {
        this.messageKey = messageKey;
        this.messageKeyValues = messageKeyValues;
    }

    /**
     * Gets the message key.
     * 
     * @return the key
     */
    public String getMessageKey() {
        return messageKey;
    }

    /**
     * Gets the message key values (i.e. substitution values).
     * 
     * @return a list of the values
     */
    public List<String> getMessageKeyValues() {
        return messageKeyValues;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((messageKey == null) ? 0 : messageKey.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        KeyedMessage other = (KeyedMessage) obj;
        if (messageKey == null) {
            if (other.messageKey != null)
                return false;
        } else if (!messageKey.equals(other.messageKey))
            return false;
        return true;
    }

}