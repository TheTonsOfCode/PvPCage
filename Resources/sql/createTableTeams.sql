CREATE TABLE IF NOT EXISTS `PartyDataPvPCage` (
    `id` INT(6) NOT NULL AUTO_INCREMENT,
    `tag` VARCHAR(10) NOT NULL UNIQUE,
    `name` VARCHAR(36) NOT NULL UNIQUE,
    `leader` VARCHAR(36),
    `mod` VARCHAR(36),
    `lifetime` BIGINT(100),
    `createtime` BIGINT(100),
    PRIMARY KEY (`id`),
    KEY (`tag`, `name`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
