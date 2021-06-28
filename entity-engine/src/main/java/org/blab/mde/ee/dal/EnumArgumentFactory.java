package org.blab.mde.ee.dal;

import java.sql.Types;

import org.jdbi.v3.core.argument.AbstractArgumentFactory;
import org.jdbi.v3.core.argument.Argument;
import org.jdbi.v3.core.config.ConfigRegistry;


public class EnumArgumentFactory extends AbstractArgumentFactory<Enum<?>> {

	public EnumArgumentFactory() {
		super(Types.VARCHAR);
	}

	@Override
	protected Argument build(Enum<?> value, ConfigRegistry config) {
		return new EnumArgument(value);
	}
}
