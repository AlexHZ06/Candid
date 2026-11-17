import Radial from "./radial"
import { useState } from "react"

function TagsCatSelection({hide}){


    return(

        <div className={`
        
            flex
            flex-col
            bg-white
            rounded-md
            w-[100vh]
            h-fit
            shadow-[0_0_10px_rgba(0,0,0,0.25)]
            items-center
            pb-5
            ${hide === true ? "hidden": ""}
        
        `}>
            <div className="
            
                flex
                flex-col
                pb-5
                w-[90vh]
            
            ">
                <div className="
                
                    pt-5
                    flex
                    flex-row
                    justify-center
                    items-center
                    gap-3
                
                ">   
                    <select defaultValue={""} name="" className="
                        border
                        border-neutral-400
                        rounded-xl
                        hover:shadow-[0_0_10px_rgba(0,0,0,0.25)]
                        focus:outline-none
                        focus:ring-0
                        focus:border-neutral-400    
                    ">
                        <option disabled value="">Photographer Rating</option>
                        <option value="1">1 star</option>
                        <option value="2">2 star</option>
                        <option value="3">3 star</option>
                        <option value="4">4 star</option>
                        <option value="5">5 star</option>
                        <option value="0">Mixed</option>
                    </select>
                    <select defaultValue={""} name="" className="
                        border
                        border-neutral-400
                        rounded-xl
                        hover:shadow-[0_0_10px_rgba(0,0,0,0.25)]
                        focus:outline-none
                        focus:ring-0
                        focus:border-neutral-400    
                    ">
                        <option disabled value="">Order By</option>
                        <option value="Newest">Newest</option>
                        <option value="Oldest">Oldest</option>
                        <option value="Price">Price</option>
                        <option value="Rating">Rating</option>
                        <option value="none">none</option>
                    </select>
                    
                </div>
                <div className="
                
                    pt-5
                    flex
                    flex-row
                    justify-center
                    items-center
                    gap-3
                
                ">   
                    <div className="
                    
                        flex
                        flex-row
                        justify-center
                        
                    ">
                        <label className="
                            
                            pr-3
                            
                        ">Distance Radius: {} km</label>
                        <input type="range" min="1" max="1000" value={1} onChange={(e)=> setMaxCost(Number(e.target.value))} className="
                            
                            accent-neutral-500
                            
                        "/>
                    </div> 
                </div>
            </div>
        </div>

    )

}

export default TagsCatSelection