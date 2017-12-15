package threewks.framework.search;

import com.atomicleopard.expressive.ETransformer;
import com.googlecode.objectify.Ref;

import static threewks.util.ObjectUtils.ifNotNull;

public class RefToString implements ETransformer<Ref, String> {

	@Override
	public String from(Ref from) {
	    return ifNotNull(from, value -> value.getKey().toWebSafeString());
	}

}
