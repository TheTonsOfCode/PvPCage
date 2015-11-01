CREATE TABLE IF NOT EXISTS `PartyDataPvPCage` (
    `id` INT(6) NOT NULL AUTO_INCREMENT,
    `tag` VARCHAR(6) NOT NULL UNIQUE,
    `name` VARCHAR(16) NOT NULL UNIQUE,
    `leader` VARCHAR(36),
    `mod` VARCHAR(36),
    `invited` VARCHAR(100),
    PRIMARY KEY (`id`),
    KEY (`tag`, `name`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
