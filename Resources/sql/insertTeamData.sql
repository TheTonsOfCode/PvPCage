INSERT INTO `TeamDataPvPCage` (`tag`, `name`, `leader`, `mod`, `lifetime`,`createtime`)
VALUES (?,?,?,?,?,?) ON DUPLICATE KEY UPDATE
`tag`=VALUES(`tag`),
`name`=VALUES(`name`),
`leader`=VALUES(`leader`),
`mod`=VALUES(`mod`),
`lifetime`=VALUES(`lifetime`),
`createtime`=VALUES(`createtime`)
