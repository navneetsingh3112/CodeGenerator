CREATE TABLE `heirarchy_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Auto generated unique identifier. Will be the primary key of the table.',
  `level_name` VARCHAR(16) NOT NULL COMMENT 'Name of heirarchy level',
  `level_code` VARCHAR(8) NOT NULL COMMENT 'Heirarchy level code',
  `description` VARCHAR(256) COMMENT 'Heirarchy level description',
  `department_name` VARCHAR(256) NOT NULL COMMENT 'Heirarchy level department name',
  `heirarchy_level_type` VARCHAR(256) NOT NULL COMMENT 'Type of heirarchy level',
  `created_on` DATETIME NOT NULL COMMENT 'Created on',
  `created_by` VARCHAR(64) NOT NULL COMMENT 'Created by',
  `updated_on` DATETIME NOT NULL COMMENT 'Updated on',
  `updated_by` VARCHAR(64) NOT NULL COMMENT 'Updated on',
  PRIMARY KEY (`id`)
) COMMENT = 'Heirarchy level information for various purposes spanning various departements';