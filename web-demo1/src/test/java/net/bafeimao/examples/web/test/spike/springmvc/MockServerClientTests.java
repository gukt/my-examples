package net.bafeimao.examples.web.test.spike.springmvc;

import net.bafeimao.examples.web.test.common.AbstractClientTest;
import org.junit.Before;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.MockRestServiceServer.createServer;

public class MockServerClientTests extends AbstractClientTest {

    private MockRestServiceServer mockServer;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        // 模拟一个服务器
        mockServer = createServer(restTemplate);
    }

    // TODO 临时的注释掉，稍后请打开测试

//    @Test
//    public void testFindById() throws JsonProcessingException {
//        String uri = baseUri + "/{id}";
//        Long id = 1L;
//        User user = new User();
//        user.setId(1L);
//        user.setName("zhang");
//        String userJson = objectMapper.writeValueAsString(user);
//        String requestUri = UriComponentsBuilder.fromUriString(uri).buildAndExpand(id).toUriString();
//
//        // 添加服务器端断言
//        mockServer
//                .expect(requestTo(requestUri))
//                .andExpect(method(HttpMethod.GET))
//                .andRespond(withSuccess(userJson, MediaType.APPLICATION_JSON));
//
//        // 2、访问URI（与API交互）
//        ResponseEntity<User> entity = restTemplate.getForEntity(uri, User.class, id);
//
//        // 3.1、客户端验证
//        assertEquals(HttpStatus.OK, entity.getStatusCode());
//        assertThat(entity.getHeaders().getContentType().toString(), anything(MediaType.APPLICATION_JSON_VALUE));
//        assertThat(entity.getBody(), hasProperty("name", is("zhang")));
//
//        // 3.2、服务器端验证（验证之前添加的服务器端断言）
//        mockServer.verify();
//    }
//
//    @Test
//    public void testSaveWithJson() throws Exception {
//        User user = new User();
//        user.setId(1L);
//        user.setName("zhang");
//        String userJson = objectMapper.writeValueAsString(user);
//
//        String uri = baseUri;
//        String createdLocation = baseUri + "/" + 1;
//
//        mockServer
//                .expect(requestTo(uri)) // 验证请求URI
//                .andExpect(jsonPath("$.name").value(user.getName())) // 验证请求的JSON数据
//                .andRespond(
//                        withCreatedEntity(URI.create(createdLocation)).body(userJson).contentType(
//                                MediaType.APPLICATION_JSON)); // 添加响应信息
//
//        restTemplate.setMessageConverters(Arrays.<HttpMessageConverter<?>>asList(new MappingJackson2HttpMessageConverter()));
//        ResponseEntity<User> responseEntity = restTemplate.postForEntity(uri, user, User.class);
//
//        assertEquals(createdLocation, responseEntity.getHeaders().get("Location").get(0));
//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//        assertEquals(user, responseEntity.getBody());
//
//        mockServer.verify();
//    }
//
//    @Test
//    public void testSaveWithXML() throws Exception {
//        User user = new User();
//        user.setId(2L);
//        user.setName("zhang");
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        marshaller.marshal(user, new StreamResult(bos));
//        String userXml = bos.toString();
//
//        String uri = baseUri;
//        String createdLocation = baseUri + "/" + 1;
//
//        mockServer
//                .expect(requestTo(uri)) // 验证请求URI
//                .andExpect(xpath("/user/name/text()").string(user.getName())) // 验证请求的JSON数据
//                .andRespond(
//                        withCreatedEntity(URI.create(createdLocation)).body(userXml).contentType(
//                                MediaType.APPLICATION_XML)); // 添加响应信息
//
//        restTemplate.setMessageConverters(Arrays
//                .<HttpMessageConverter<?>>asList(new Jaxb2RootElementHttpMessageConverter()));
//        ResponseEntity<User> responseEntity = restTemplate.postForEntity(uri, user, User.class);
//
//        assertEquals(createdLocation, responseEntity.getHeaders().get("Location").get(0));
//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//
//        assertEquals(user, responseEntity.getBody());
//
//        mockServer.verify();
//    }
//
//    @Test
//    public void testUpdate() throws Exception {
//        User user = new User();
//        user.setId(2L);
//        user.setName("zhang");
//
//        String uri = baseUri + "/{id}";
//
//        mockServer
//                .expect(requestTo(uri)) // 验证请求URI
//                .andExpect(jsonPath("$.name").value(user.getName())) // 验证请求的JSON数据
//                .andRespond(withNoContent()); // 添加响应信息
//
//        restTemplate.setMessageConverters(Arrays
//                .<HttpMessageConverter<?>>asList(new MappingJackson2HttpMessageConverter()));
//        ResponseEntity responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity(user), (Class) null,
//                user.getId());
//
//        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
//
//        mockServer.verify();
//    }
//
//    @Test
//    public void testDelete() throws Exception {
//        String uri = baseUri + "/{id}";
//        Long id = 1L;
//
//        mockServer
//                .expect(requestTo(baseUri + "/" + id)) // 验证请求URI
//                .andRespond(withSuccess()); // 添加响应信息
//
//        ResponseEntity responseEntity = restTemplate.exchange(uri, HttpMethod.DELETE, HttpEntity.EMPTY, (Class) null,
//                id);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//
//        mockServer.verify();
//    }
//
//    @RestController
//    @RequestMapping("/user")
//    static class SimpleController {
//        @Autowired
//        private UserService userService;
//
//        @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//        public User findById(@PathVariable("id") Long id) {
//            return userService.findById(id);
//        }
//
//        @SuppressWarnings("unchecked")
//        @RequestMapping(method = RequestMethod.POST)
//        public ResponseEntity<User> save(@RequestBody User user, UriComponentsBuilder uriComponentsBuilder) {
//            // save user
//            user.setId(1L);
//            MultiValueMap headers = new HttpHeaders();
//            headers
//                    .set("Location", uriComponentsBuilder.path("/users/{id}").buildAndExpand(user.getId())
//                            .toUriString());
//            return new ResponseEntity(user, headers, HttpStatus.CREATED);
//        }
//
//        @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
//        @ResponseStatus(HttpStatus.NO_CONTENT)
//        public void update(@RequestBody User user) {
//            // update by id
//        }
//
//        @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//        public void delete(@PathVariable("id") Long id) {
//            // delete by id
//        }
//
//    }
}