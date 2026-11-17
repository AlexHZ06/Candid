
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

        fetch("/api/album/getallalbums", {

            method:"GET",
            headers:{

                auth: localStorage.getItem("jwt")

            }
        }).then(response => response.json()).then(data => {

            setAlbums(data.data)
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
                        xl:mt-2
                        xl:pb-2
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
                        
                        {albums?.map(album => (

                            <Link to={`/album/${album.albumid}/${album.albumname}`} key={album.albumid} className="
                            
                                bg-neutral-200
                                w-full
                                aspect-square
                                rounded-xl
                                shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                                hover:shadow-[0_0px_10px_rgba(0,0,0,0.5)]
                                transition
                                duration-100
                                active:shadow-[0_0px_10px_rgba(0,0,0,0.25)_inset]
                                relative
                                overflow-hidden

                            ">

                                <img src={"/api" + album.thumnail} className="
                                
                                    absolute
                                    w-full
                                    h-full
                                    object-cover
                                    rounded-xl
                                    top-0
                                    left-0
                                
                                "/>

                                <div className="
                                
                                    absolute
                                    bottom-0
                                    left-0
                                    w-full
                                    bg-black/50
                                    p-3

                                ">
                                    <p className="
                                    
                                        text-white
                                        text-lg
                                        font-semibold
                                    
                                    ">{album.albumname}</p>
                                </div>

                            </Link>

                        ))}

                    </div>

                    <div className="
                        w-10/10
                        flex
                        flex-row
                        items-center
                        justify-center
                        mt-5
                        xl:mt-2
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
                            xl:h-[5vh]
                            xl:w-[15vh]
                        ">Add Album</Link>
                    </div>

                </div>
            </div>
        </div>

    )

}

export default Gallery
