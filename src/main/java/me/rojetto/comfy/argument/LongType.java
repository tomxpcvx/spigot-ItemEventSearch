package me.rojetto.comfy.argument;

import me.rojetto.comfy.exception.ArgumentParseException;

/**
 * Created by tarik on 18.12.2016.
 */
public class LongType extends RangedNumberType<Long> {
    @Override
    public Long parse(String segment) throws ArgumentParseException {
        long l;

        try {
            l = Long.parseLong(segment);
        } catch (NumberFormatException e) {
            throw new ArgumentParseException("'" + segment + "' is not a valid long.");
        }

        checkRange(l);
        return l;
    }

    @Override
    public boolean matches(String segment) {
        try {
            Long.parseLong(segment);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
