CREATE TABLE ADDITION_CACHE (
  OPERANDS VARCHAR(500) NOT NULL COMMENT 'Operands stored in a sorted manner, joined by \',\'',
  RESULT DOUBLE PRECISION NOT NULL,
  FULLTEXT IDX(OPERANDS));