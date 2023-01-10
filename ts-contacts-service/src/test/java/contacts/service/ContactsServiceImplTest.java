package contacts.service;

import contacts.entity.Contacts;
import contacts.repository.ContactsRepository;
import edu.fudan.common.util.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RunWith(JUnit4.class)
public class ContactsServiceImplTest {

    @InjectMocks
    private ContactsServiceImpl contactsServiceImpl;

    @Mock
    private ContactsRepository contactsRepository;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindContactsById1() {
        UUID id = UUID.randomUUID();
        Contacts contacts = new Contacts();
        Mockito.when(contactsRepository.findById(id.toString())).thenReturn(Optional.of(contacts));
        Response result = contactsServiceImpl.findContactsById(id.toString(), headers);
        Assert.assertEquals(new Response<>(1, "Success", contacts), result);
    }

    @Test
    public void testFindContactsById2() {
        UUID id = UUID.randomUUID();
        Mockito.when(contactsRepository.findById(id.toString())).thenReturn(Optional.empty());
        Response result = contactsServiceImpl.findContactsById(id.toString(), headers);
        Assert.assertEquals(new Response<>(0, "No contacts according to contacts id", null), result);
    }

    @Test
    public void testFindContactsByAccountId() {
        UUID accountId = UUID.randomUUID();
        ArrayList<Contacts> arr = new ArrayList<>();
        Mockito.when(contactsRepository.findByAccountId(accountId.toString())).thenReturn(arr);
        Response result = contactsServiceImpl.findContactsByAccountId(accountId.toString(), headers);
        Assert.assertEquals(new Response<>(1, "Success", arr), result);
    }

    @Test
    public void testCreateContacts1() {
        UUID id = UUID.randomUUID();
        Contacts contacts = new Contacts(id.toString(), id.toString(), "name", 1, "12", "10001");
        Mockito.when(contactsRepository.findByAccountIdAndDocumentTypeAndDocumentType(id.toString(), "12", 1)).thenReturn(contacts);
        Response result = contactsServiceImpl.createContacts(contacts, headers);
        Assert.assertEquals(new Response<>(0, "Already Exists", contacts), result);
    }

    @Test
    public void testCreateContacts2() {
        Contacts contacts = new Contacts();
        Mockito.when(contactsRepository.findByAccountIdAndDocumentTypeAndDocumentType(Mockito.any(String.class), Mockito.any(String.class), Mockito.anyInt())).thenReturn(null);
        Mockito.when(contactsRepository.save(Mockito.any(Contacts.class))).thenReturn(null);
        Response result = contactsServiceImpl.createContacts(contacts, headers);
        Assert.assertEquals(new Response<>(1, "Create Success", null), result);
    }

    @Test
    public void testCreate1() {
        UUID id = UUID.randomUUID();
        Contacts contacts = new Contacts(id.toString(), id.toString(), "name", 1, "12", "10001");
        Mockito.when(contactsRepository.findByAccountIdAndDocumentTypeAndDocumentType(id.toString(), "12", 1)).thenReturn(contacts);
        Response result = contactsServiceImpl.create(contacts, headers);
        Assert.assertEquals(new Response<>(0, "Contacts already exists", null), result);
    }

    @Test
    public void testCreate2() {
        Contacts contacts = new Contacts();
        Mockito.when(contactsRepository.findByAccountIdAndDocumentTypeAndDocumentType(Mockito.any(String.class), Mockito.any(String.class), Mockito.anyInt())).thenReturn(null);
        Mockito.when(contactsRepository.save(Mockito.any(Contacts.class))).thenReturn(contacts);
        Response result = contactsServiceImpl.create(contacts, headers);
        Assert.assertEquals(new Response<>(1, "Create contacts success", contacts), result);
    }

    @Test
    public void testDelete1() {
        UUID contactsId = UUID.randomUUID();
        Mockito.doNothing().doThrow(new RuntimeException()).when(contactsRepository).deleteById(Mockito.any(String.class));
        Mockito.when(contactsRepository.findById(Mockito.any(String.class))).thenReturn(Optional.empty());
        Response result = contactsServiceImpl.delete(contactsId.toString(), headers);
        Assert.assertEquals(new Response<>(1, "Delete success", contactsId.toString()), result);
    }

    @Test
    public void testDelete2() {
        UUID contactsId = UUID.randomUUID();
        Contacts contacts = new Contacts();
        Mockito.doNothing().doThrow(new RuntimeException()).when(contactsRepository).deleteById(Mockito.any(String.class));
        Mockito.when(contactsRepository.findById(Mockito.any(String.class))).thenReturn(Optional.of(contacts));
        Response result = contactsServiceImpl.delete(contactsId.toString(), headers);
        Assert.assertEquals(new Response<>(0, "Delete failed", contactsId.toString()), result);
    }

    @Test
    public void testModify1() {
        Contacts contacts = new Contacts(UUID.randomUUID().toString(), UUID.randomUUID().toString(), "name", 1, "12", "10001");
        Mockito.when(contactsRepository.findById(Mockito.any(String.class))).thenReturn(Optional.empty());
        Response result = contactsServiceImpl.modify(contacts, headers);
        Assert.assertEquals(new Response<>(0, "Contacts not found", null), result);
    }

    @Test
    public void testModify2() {
        Contacts contacts = new Contacts(UUID.randomUUID().toString(), UUID.randomUUID().toString(), "name", 1, "12", "10001");
        Mockito.when(contactsRepository.findById(Mockito.any(String.class))).thenReturn(Optional.of(contacts));
        Mockito.when(contactsRepository.save(Mockito.any(Contacts.class))).thenReturn(null);
        Response result = contactsServiceImpl.modify(contacts, headers);
        Assert.assertEquals(new Response<>(1, "Modify success", contacts), result);
    }

    @Test
    public void testGetAllContacts1() {
        ArrayList<Contacts> contacts = new ArrayList<>();
        contacts.add(new Contacts());
        Mockito.when(contactsRepository.findAll()).thenReturn(contacts);
        Response result = contactsServiceImpl.getAllContacts(headers);
        Assert.assertEquals(new Response<>(1, "Success", contacts), result);
    }

    @Test
    public void testGetAllContacts2() {
        Mockito.when(contactsRepository.findAll()).thenReturn(null);
        Response result = contactsServiceImpl.getAllContacts(headers);
        Assert.assertEquals(new Response<>(0, "No content", null), result);
    }

}
