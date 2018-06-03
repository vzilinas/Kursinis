SELECT S.Name, SUM((P.Price + (P.Price * P.Tax / 1000)) * B.Amount ) 
FROM dbo.Shop AS S
	LEFT JOIN dbo.Product AS P ON P.ShopId = S.Id
	INNER JOIN dbo.Buys AS B ON P.Id = B.ProductId 
GROUP BY S.Name
