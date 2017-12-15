package threewks.framework.search;

import com.atomicleopard.expressive.ETransformer;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeToBigDecimal implements ETransformer<OffsetDateTime, BigDecimal> {

	@Override
	public BigDecimal from(OffsetDateTime from) {
		return from == null ? null : new BigDecimal(from.atZoneSameInstant(ZoneOffset.UTC).toInstant().toEpochMilli());
	}

}
