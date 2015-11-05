CREATE TABLE IF NOT EXISTS `VictimsDataPvPCage` (
    `id` INT(6) NOT NULL AUTO_INCREMENT,
    `killer_uuid` VARCHAR(36) NOT NULL,
    `victim_uuid` VARCHAR(36) NOT NULL,
    PRIMARY KEY (`id`),
    KEY (`killer_uuid`, `victim_uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
