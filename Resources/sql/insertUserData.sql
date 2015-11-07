INSERT INTO `UserDataPvPCage` (`uuid`, `name`, `team`, `loseduel`, `winduel`,`escapeduel`,`points`)
VALUES (?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE
`uuid`=VALUES(`uuid`),
`name`=VALUES(`name`),
`team`=VALUES(`team`),
`loseduel`=VALUES(`loseduel`),
`winduel`=VALUES(`winduel`),
`escapeduel`=VALUES(`escapeduel`),
`points`=VALUES(`points`)
