CREATE TABLE IF NOT EXISTS `MembersDataPvPCage` (
    `id` INT(6) NOT NULL AUTO_INCREMENT,
    `tag` VARCHAR(6) NOT NULL,
    `uuid` VARCHAR(36) NOT NULL,
    PRIMARY KEY (`id`),
    KEY (`tag`, `uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
