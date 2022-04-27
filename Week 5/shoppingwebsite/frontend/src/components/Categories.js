import React from 'react'
import { Route } from 'react-router-dom'
import { useDispatch, useSelector } from 'react-redux'
import { LinkContainer } from 'react-router-bootstrap'
import { Navbar, Nav, Container, NavDropdown } from 'react-bootstrap'
import SearchBox from './SearchBox'
import { logout } from '../actions/userActions'
import {useState} from 'react'  

function Categories() {
    const dispatch = useDispatch()
    const [category, setCategory] = useState('')

    let categories = ['Electronics', 'Clothing', 'Books', 'Sports', 'Movies', 'Games', 'Tools', 'Home', 'Other']


  return (
    <div>
        <NavDropdown title='Category'id='categories'>
            {categories.map((category) => (
                <LinkContainer to={`/search?Cg=${category}`}>
                    <NavDropdown.Item key={category} onClick={() => setCategory(category)}>
                        {category}
                    </NavDropdown.Item>
                </LinkContainer>
            ))}
        </NavDropdown>
    </div>
  )
}

export default Categories