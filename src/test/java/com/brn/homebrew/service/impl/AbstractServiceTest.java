package com.brn.homebrew.service.impl;

import com.brn.homebrew.dao.Dao;
import com.brn.homebrew.service.Service;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Bruno Domingues
 */
public class AbstractServiceTest {

    private Dao daoMock;

    private AbstractServiceImpl service;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        daoMock = mock(Dao.class);
        service = new AbstractServiceImpl();
        service.setDao(daoMock);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldCreate() throws Exception {
        //given
        Entity entity = new Entity();
        entity.setAttribute("testing");
        //when
        service.create(entity);
        //then
        verify(daoMock).create(entity);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldRead() throws Exception {
        //given
        Entity expected = new Entity();
        expected.setId(1l);
        when(daoMock.read(1l)).thenReturn(expected);
        //when
        Entity actual = service.read(1l);
        //then
        assertEquals(expected, actual);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldUpdate() throws Exception {
        //given
        Entity entity = new Entity();
        entity.setId(1l);
        //when
        service.update(entity);
        //then
        verify(daoMock).update(entity);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldDelete() throws Exception {
        //given
        Entity entity = new Entity();
        entity.setId(1l);
        //when
        service.delete(entity);
        //then
        verify(daoMock).delete(entity);
    }

    private class Entity {

        private Long id;
        private String attribute;

        public void setId(Long id) {
            this.id = id;
        }

        public void setAttribute(String attribute) {
            this.attribute = attribute;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Entity entity = (Entity) o;

            return !(attribute != null ? !attribute.equals(entity.attribute) : entity.attribute != null)
                    && !(id != null ? !id.equals(entity.id) : entity.id != null);

        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (attribute != null ? attribute.hashCode() : 0);
            return result;
        }
    }

    private class AbstractServiceImpl extends AbstractService<Entity> implements Service<Entity> {
    }
}