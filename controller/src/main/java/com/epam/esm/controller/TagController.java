package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.model.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.util.Constant;
import com.epam.esm.util.PaginationValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/tags", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

  private static final Logger LOGGER = Logger.getRootLogger();

  private TagService<Tag, TagDTO, Long> tagService;

  @Autowired
  public TagController(TagService tagService) {
    this.tagService = tagService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAuthority('ADMIN')")
  public void create(
      @Valid @RequestBody TagDTO tagDTO, HttpServletResponse response, HttpServletRequest request) {

    Long id = tagService.create(tagDTO).getId();
    response.addHeader("Location", request.getRequestURL() + "/" + id);
  }

  @PutMapping(value = "/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public TagDTO update(
      @PathVariable("id") Long id,
      @Valid @RequestBody TagDTO tagDTO,
      HttpServletResponse response) {

    tagDTO.setId(id);
    tagService.update(tagDTO);
    return tagService.getById(id);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasAuthority('ADMIN')")
  public void delete(@PathVariable Long id) {
    tagService.delete(id);
  }

  @GetMapping(value = "/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public TagDTO getById(@PathVariable Long id) {
    return tagService.getById(id);
  }

  @GetMapping
  @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
  public List<TagDTO> getAll(
      @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "size", required = false, defaultValue = "100") Integer size,
      HttpServletResponse response) {

    LOGGER.debug("TagController : getAll : page: " + page + ", size: " + size);

    if (!PaginationValidator.isPageValid(page)) {
      page = Constant.PAGE;
    }

    if (!PaginationValidator.isSizeValid(size)) {
      size = Constant.DEFAULT_SIZE;
    }

    List<TagDTO> tags = tagService.getAll(page.intValue(), size.intValue());
    response.addHeader("X-Total-Count", tagService.getEntityCount().toString());
    response.addHeader("Access-Control-Expose-Headers", "X-Total-Count");
    return tags;
  }
}
