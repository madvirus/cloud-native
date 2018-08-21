--
-- Table structure for table `association_value_entry`
--

DROP TABLE IF EXISTS `association_value_entry`;
CREATE TABLE `association_value_entry` (
  `id` bigint(20) NOT NULL,
  `association_key` varchar(255) NOT NULL,
  `association_value` varchar(255) DEFAULT NULL,
  `saga_id` varchar(255) NOT NULL,
  `saga_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `domain_event_entry`
--

DROP TABLE IF EXISTS `domain_event_entry`;
CREATE TABLE `domain_event_entry` (
  `global_index` bigint(20) NOT NULL,
  `event_identifier` varchar(255) NOT NULL,
  `meta_data` longblob,
  `payload` longblob NOT NULL,
  `payload_revision` varchar(255) DEFAULT NULL,
  `payload_type` varchar(255) NOT NULL,
  `time_stamp` varchar(255) NOT NULL,
  `aggregate_identifier` varchar(255) NOT NULL,
  `sequence_number` bigint(20) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`global_index`),
  UNIQUE KEY `UK8s1f994p4la2ipb13me2xqm1w` (`aggregate_identifier`,`sequence_number`),
  UNIQUE KEY `UK_fwe6lsa8bfo6hyas6ud3m8c7x` (`event_identifier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
create table hibernate_sequence (
    next_val INTEGER NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into hibernate_sequence values (1);

--
-- Table structure for table `saga_entry`
--

DROP TABLE IF EXISTS `saga_entry`;
CREATE TABLE `saga_entry` (
  `saga_id` varchar(255) NOT NULL,
  `revision` varchar(255) DEFAULT NULL,
  `saga_type` varchar(255) DEFAULT NULL,
  `serialized_saga` longblob,
  PRIMARY KEY (`saga_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `snapshotevententry`
--

DROP TABLE IF EXISTS `snapshot_event_entry`;
CREATE TABLE `snapshot_event_entry` (
  `aggregateIdentifier` varchar(255) NOT NULL,
  `sequenceNumber` bigint(20) NOT NULL,
  `type` varchar(255) NOT NULL,
  `eventIdentifier` varchar(255) NOT NULL,
  `metaData` longblob,
  `payload` longblob NOT NULL,
  `payloadRevision` varchar(255) DEFAULT NULL,
  `payloadType` varchar(255) NOT NULL,
  `timeStamp` varchar(255) NOT NULL,
  PRIMARY KEY (`aggregateIdentifier`,`sequenceNumber`,`type`),
  UNIQUE KEY `UK_sg7xx45yh4ajlsjd8t0uygnjn` (`eventIdentifier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `token_entry`
--

DROP TABLE IF EXISTS `token_entry`;
CREATE TABLE `token_entry` (
  `processor_name` varchar(255) NOT NULL,
  `segment` int(11) NOT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `timestamp` varchar(255) NOT NULL,
  `token` longblob,
  `token_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`processor_name`,`segment`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

