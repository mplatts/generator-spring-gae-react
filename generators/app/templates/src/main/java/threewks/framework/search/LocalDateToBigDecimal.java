package threewks.framework.search;

import com.atomicleopard.expressive.ETransformer;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LocalDateToBigDecimal implements ETransformer<LocalDate, BigDecimal> {

	@Override
	public BigDecimal from(LocalDate from) {
		return from == null ? null : new BigDecimal(from.toEpochDay());
	}

}
