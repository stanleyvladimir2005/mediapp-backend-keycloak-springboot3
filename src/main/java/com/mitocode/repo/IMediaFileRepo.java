package com.mitocode.repo;

import com.mitocode.model.MediaFile;
import org.springframework.stereotype.Repository;

@Repository
public interface IMediaFileRepo extends IGenericRepo<MediaFile, Integer> {}