import PHeader from "../compnents/pheader"
import { Link } from "react-router-dom"
import AlbumFolder from "../compnents/albumFolder"
import {useEffect } from "react"
import { jwtService } from "../logic/jwt"
import { useNavigate } from "react-router-dom"
import { useState } from "react"

function Gallery(){

    const tokenService = new jwtService()
    const [albums, setAlbums] = useState([])

    useEffect(() => {

        tokenService.checkTokenPhotographer()

        fetch("/api/album/photographer/getallalbums", {

            method:"GET",
            headers:{

                auth: localStorage.getItem("jwt")

            }
        }).then(response => response.json()).then(data => {

            setAlbums(data)
            console.log(data)

        })

    }, [])

    return(

        <div className="
        
            w-screen 
            min-h-screen 
        
        ">
            <PHeader active="Gallery"/>
            <div className="

                flex
                felx-col
                items-center
                justify-center

            ">
                <div className="
                
                    flex
                    border
                    border-neutral-300
                    w-8/10
                    h-[80vh]
                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                    flex-col
                    items-center
                    rounded-md
                    mt-10
                
                ">
                    <div className="
                    
                        w-9/10
                        flex
                        mt-5
                        justify-center
                        items-center
                    
                    ">
                        <p className="
                        
                            font-semibold
                            tracking-wide
                            text-neutral-700
                            text-2xl
                        
                            
                        
                        ">Gallery</p>
                    </div>
                        <div className="
                    
                        border
                        border-neutral-300
                        rounded-xl
                        w-9/10
                        h-[80%]
                        pt-5
                        shadow-[0_0px_10px_rgba(0,0,0,0.25)_inset]
                        flex
                        flex-row
                        
                        gap-4
                        overflow-y-auto
              
                        justify-center
                        shrink-0
                        flex-wrap
                        
                        
                        content-start
           
                    
                    ">
                        
                        {albums.map(album => (

                            <AlbumFolder title={album.albumname} photos={album.numofphotos} id={album.albumid} thumbnail={"/api" + album.thumnail}/>

                        ))}

                    </div>
                    <div className="
                    
                        w-10/10
                        flex
                        flex-row
                        items-center
                        justify-center
                        mt-5
                    ">

                        <Link to={"/albumcreate"} className="
                        
                            bg-black
                            text-white
                            rounded-xl
                            w-[10vh]
                            h-8.75
                            flex
                            items-center
                            justify-center
                             hover:bg-gray-900
      
                            transition
                            duration-100

                            shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                            active:bg-white
                            active:text-black

                        
                        ">Add Album</Link>

                    </div>

                </div>
            </div>
        </div>

    )

}

export default Gallery