/*
 * HyperQueue API
 * Network enabled in-memory event queuing system.
 */

package hyperqueue.resources;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.databind.util.ISO8601Utils;

import java.text.FieldPosition;
import java.util.Date;

public class CustomDateFormat extends ISO8601DateFormat {

	private static final long serialVersionUID = 5320819623108963388L;

	// Same as ISO8601DateFormat but serializing milliseconds.
    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        String value = ISO8601Utils.format(date, true);
        toAppendTo.append(value);
        return toAppendTo;
    }

}
