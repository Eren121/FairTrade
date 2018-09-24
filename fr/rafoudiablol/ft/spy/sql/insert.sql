INSERT INTO Offers VALUES(NULL, :uuid0, :items0, :money0);
INSERT INTO Offers VALUES(NULL, :uuid1, :items1, :money1);

INSERT INTO Trades VALUES(NULL, (SELECT MAX(offerID) FROM Offfers) - 1, DATETIME('now'));