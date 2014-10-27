package com.ptsisi.hibernate;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.dialect.Dialect;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhaoding on 14-10-24.
 */
public class TableSeqGenerator extends SequenceStyleGenerator {

	private static final Logger logger = LoggerFactory
			.getLogger(TableSeqGenerator.class);

	private static final int MaxLength = 30;

	/** 序列命名模式 */
	private String sequencePattern = "seq_{table}";

	public void configure(Type type, Properties params, Dialect dialect) {
		if (StringUtils.isEmpty(params.getProperty(SEQUENCE_PARAM))) {
			String tableName = params
					.getProperty(PersistentIdentifierGenerator.TABLE);
			String pk = params.getProperty(PersistentIdentifierGenerator.PK);
			if (null != tableName) {
				String seqName = StringUtils.replace(sequencePattern,
						"{table}", tableName);
				seqName = StringUtils.replace(seqName, "{pk}", pk);
				if (seqName.length() > MaxLength) {
					logger.warn(
							"{}'s length >=30, wouldn't be supported in some db!",
							seqName);
				}
				params.setProperty(SEQUENCE_PARAM, seqName);
			}
		}
		super.configure(type, params, dialect);
	}
}
