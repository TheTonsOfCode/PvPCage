CREATE TABLE IF NOT EXISTS `UserDataPvPCage` (
    `id` INT(6) NOT NULL AUTO_INCREMENT,
    `uuid` VARCHAR(36) NOT NULL UNIQUE,
    `name` VARCHAR(20) NOT NULL UNIQUE,
    `loseduel` INT(12),
    `team` VARCHAR(6),
    `winduel` INT(12),
    `escapeduel` INT(12),
    `points` INT(12),
    `victims` VARCHAR(100),
    PRIMARY KEY (`id`),
    KEY (`name`, `uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;