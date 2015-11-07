INSERT INTO `VictimsDataPvPCage` (`killer_uuid`, `victim_uuid`) 
VALUES (?,?) ON DUPLICATE KEY UPDATE 
`killer_uuid`=VALUES(`killer_uuid`),
`victim_uuid`=VALUES(`victim_uuid`)
