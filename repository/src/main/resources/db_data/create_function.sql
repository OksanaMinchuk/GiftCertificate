CREATE OR REPLACE FUNCTION find_and_sort_certificates(wantedTag character varying,
													  queryPart character varying,
													  sortBy character varying,
													  descending boolean)
RETURNS SETOF certificate
    LANGUAGE plpgsql
	AS $$
    BEGIN
         IF wantedTag <> '' AND queryPart <> '' THEN
            RETURN QUERY SELECT certificate.id, gift_name, description, price, date_of_creation, date_of_modification, duration
   		  	FROM (certificate
   		  	JOIN tag_certificate ON certificate.id = tag_certificate.certificate_id)
   		  	JOIN tag ON tag.id = tag_certificate.tag_id
   		  		WHERE name_tag in (select unnest(string_to_array(wantedTag, ',')))
				AND (POSITION($2 IN gift_name)>0 OR POSITION($2 IN description)>0)
		  		GROUP BY tag_certificate.certificate_id,certificate.id
				HAVING count(tag_certificate.certificate_id)=array_length(string_to_array(wantedTag, ','), 1)
       		ORDER BY
       			CASE WHEN sortBy='name' AND descending IS TRUE THEN gift_name END DESC,
       			CASE WHEN sortBy='createdDate' AND descending IS TRUE THEN date_of_creation END DESC,
       			CASE WHEN sortBy='name' AND descending IS FALSE THEN gift_name END ASC,
       			CASE WHEN sortBy='createdDate' AND descending IS FALSE THEN date_of_creation END ASC,
   				CASE WHEN sortBy!='name' AND sortBy!='createdDate' THEN gift_name END ASC;
         ELSIF wantedTag <> '' THEN
            RETURN QUERY SELECT certificate.id, gift_name, description, price, date_of_creation, date_of_modification, duration
   		   	FROM certificate JOIN tag_certificate ON certificate.id = tag_certificate.certificate_id
            JOIN tag on tag.id = tag_certificate.tag_id
   		   		WHERE name_tag in (select unnest(string_to_array(wantedTag, ',')))
				GROUP BY tag_certificate.certificate_id,certificate.id
				HAVING count(tag_certificate.certificate_id)=array_length(string_to_array(wantedTag, ','), 1)
   		   ORDER BY
       			CASE WHEN sortBy='name' AND descending IS TRUE THEN gift_name END DESC,
       			CASE WHEN sortBy='createdDate' AND descending IS TRUE THEN date_of_creation END DESC,
       			CASE WHEN sortBy='name' AND descending IS FALSE THEN gift_name END ASC,
       			CASE WHEN sortBy='createdDate' AND descending IS FALSE THEN date_of_creation END ASC,
   				CASE WHEN sortBy!='name' AND sortBy!='createdDate' THEN gift_name END ASC;
         ELSIF queryPart <> '' THEN
            RETURN QUERY SELECT certificate.id, gift_name, description, price, date_of_creation, date_of_modification, duration
   		  	FROM certificate
   		  	WHERE (POSITION($2 IN gift_name)>0 OR POSITION($2 IN description)>0)
   		  	ORDER BY
       			CASE WHEN sortBy='name' AND descending IS TRUE THEN gift_name END DESC,
       			CASE WHEN sortBy='createdDate' AND descending IS TRUE THEN date_of_creation END DESC,
       			CASE WHEN sortBy='name' AND descending IS FALSE THEN gift_name END ASC,
       			CASE WHEN sortBy='createdDate' AND descending IS FALSE THEN date_of_creation END ASC,
   				CASE WHEN sortBy!='name' AND sortBy!='createdDate' THEN gift_name END ASC;
         ELSE
            RETURN QUERY SELECT certificate.id, gift_name, description, price, date_of_creation, date_of_modification, duration
   		 	FROM certificate
   		 	ORDER BY
       			CASE WHEN sortBy='name' AND descending IS TRUE THEN gift_name END DESC,
       			CASE WHEN sortBy='createdDate' AND descending IS TRUE THEN date_of_creation END DESC,
       			CASE WHEN sortBy='name' AND descending IS FALSE THEN gift_name END ASC,
       			CASE WHEN sortBy='createdDate' AND descending IS FALSE THEN date_of_creation END ASC,
   				CASE WHEN sortBy!='name' AND sortBy!='createdDate' THEN gift_name END ASC;
         END IF;
       END;
    $$;