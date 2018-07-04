ALTER TABLE `messages`
	ADD COLUMN `message_type` VARCHAR(100) NULL AFTER `province`;

UPDATE messages SET message_type='CONSULTING_COOPERATION';