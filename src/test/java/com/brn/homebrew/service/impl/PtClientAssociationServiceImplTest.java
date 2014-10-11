package com.brn.homebrew.service.impl;

import com.brn.homebrew.dao.PtClientAssociationDao;
import com.brn.homebrew.model.Client;
import com.brn.homebrew.model.PersonalTrainer;
import com.brn.homebrew.model.PtClientAssociation;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Bruno Domingues
 */
public class PtClientAssociationServiceImplTest {

    @Test
    public void shouldReadAllAssociationsFromPt() throws Exception {
        //given
        PersonalTrainer personalTrainer = new PersonalTrainer();
        personalTrainer.setId(1l);
        personalTrainer.setFirstName("John");
        personalTrainer.setLastName("Doe");
        List<Client> clientList = new ArrayList<>();
        clientList.add(createClient(2l, "Fat", "Joe"));
        clientList.add(createClient(3l, "Fatty", "McFatty"));
        clientList.add(createClient(4l, "Louise", "Blober"));
        List<PtClientAssociation> expected = new ArrayList<>();
        for (int i = 0; i < clientList.size(); ++i) {
            PtClientAssociation ptClientAssociation = new PtClientAssociation();
            ptClientAssociation.setId((long) (i + 1));
            ptClientAssociation.setPersonalTrainer(personalTrainer);
            ptClientAssociation.setClient(clientList.get(i));
            expected.add(ptClientAssociation);
        }
        PtClientAssociationDao daoMock = mock(PtClientAssociationDao.class);
        when(daoMock.readAllFromPersonalTrainer(personalTrainer)).thenReturn(expected);
        PtClientAssociationServiceImpl service = new PtClientAssociationServiceImpl();
        service.setDao(daoMock);
        //when
        List<PtClientAssociation> actual = service.readAllFromPersonalTrainer(personalTrainer);
        //then
        assertEquals(expected, actual);
    }

    private Client createClient(long id, String firstName, String lastName) {
        Client client = new Client();
        client.setId(id);
        client.setFirstName(firstName);
        client.setLastName(lastName);
        return client;
    }
}
