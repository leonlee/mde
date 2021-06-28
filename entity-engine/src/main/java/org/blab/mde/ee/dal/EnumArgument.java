package org.blab.mde.ee.dal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.jdbi.v3.core.argument.Argument;
import org.jdbi.v3.core.statement.StatementContext;


public class EnumArgument implements Argument{

		private final Enum<?> value;

		public EnumArgument(Enum<?> value) {
			this.value = value;
		}

		@Override
		public void apply(int position, PreparedStatement statement, StatementContext ctx) throws SQLException {
			if (value != null) {
				statement.setObject(position, value.name());
			} else {
				statement.setNull(position, Types.OTHER);
			}
		}
}
